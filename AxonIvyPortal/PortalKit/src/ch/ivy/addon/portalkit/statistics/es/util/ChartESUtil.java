package ch.ivy.addon.portalkit.statistics.es.util;

import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.ACTIVATOR_ID;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.APP_ID;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.BUSINESS_CASE_ID;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.BUSINESS_RUNTIME;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CASE_ID;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CATEGORY;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CREATOR_ID;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CUSTOM_NUMBER_NAME;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CUSTOM_NUMBER_VALUE;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CUSTOM_STRING_NAME;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CUSTOM_STRING_VALUE;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CUSTOM_TEXT_NAME;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CUSTOM_TEXT_VALUE;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CUSTOM_TIMESTAMP_NAME;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CUSTOM_TIMESTAMP_VALUE;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.ID;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.NO_NAME;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.QUERY_STRING_OR;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.STATE;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.TASK_ID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;

import ch.ivy.addon.portalkit.statistics.es.enums.ChartYAxisType;
import ch.ivy.addon.portalkit.statistics.es.enums.ColumnType;
import ch.ivy.addon.portalkit.statistics.es.enums.DataType;
import ch.ivy.addon.portalkit.statistics.es.model.ChartFilter;
import ch.ivy.addon.portalkit.statistics.es.search.SearchCriteria;
import ch.ivy.addon.portalkit.statistics.es.service.ChartService;
import ch.ivy.addon.portalkit.util.PermissionUtils;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.security.IPermission;
import ch.ivyteam.ivy.workflow.CaseState;
import ch.ivyteam.ivy.workflow.TaskState;

public class ChartESUtil {

  public static Map<String, Number> createChartDataset(ChartFilter filter) {
    var dataset = new HashMap<String, Number>();
    var criteria = new SearchCriteria();
    criteria.setIndexName(filter.getDataType().getType());
    buildIncludeFields(filter, criteria);

    var boolQuery = QueryBuilders.boolQuery();
    buildPermissionQuery(filter, boolQuery);
    buildAppQuery(boolQuery);
    buildStateQuery(filter, boolQuery);
    buildCategoryQuery(filter, boolQuery);
    buildCustomFieldQuery(filter, boolQuery);
    buildCustomFieldValueQuery(filter, boolQuery);
    criteria.setQuery(boolQuery);

    var foundSearchHits = ChartService.findByCriteria(criteria);
    for (var hit : foundSearchHits) {
      Ivy.log().info("Found hit {0}", hit.getSourceAsString());
      Ivy.log().info("Selected dimension {0}", filter.getxAxisData().getColumnName());
      var columnKey = filter.getxAxisData().getColumnName();
      Object dimensionData = null;
      if (ColumnType.CUSTOM_STRING == filter.getxAxisData().getColumnType()) {
        columnKey = CUSTOM_STRING_VALUE;
      }

      dimensionData = hit.getSourceAsMap().get(columnKey);
      var label = Objects.isNull(dimensionData) ? NO_NAME : dimensionData.toString();

      if (STATE.equals(filter.getxAxisData().getColumnName())) {
        if (DataType.CASE == filter.getDataType()) {
          label = CaseState.valueOf(Integer.valueOf(label).intValue()).name();
        }
        if (DataType.TASK == filter.getDataType()) {
          label = TaskState.valueOf(Integer.valueOf(label).intValue()).name();
        }
      }

      if (StringUtils.isEmpty(label)) {
        label = NO_NAME;
      }

      var value = 1;
      if (ChartYAxisType.CASES == filter.getyAxisData() || ChartYAxisType.TASKS == filter.getyAxisData()) {
        if (dataset.containsKey(label)) {
          value += dataset.get(label).intValue();
        }
      }
      if (ChartYAxisType.TIME_HOURS == filter.getyAxisData()) {
        var businessruntime = hit.getSourceAsMap().get(BUSINESS_RUNTIME);
        value = Objects.isNull(businessruntime) ? 0 : Integer.valueOf(businessruntime.toString());
      }
      dataset.put(label, value);
    }
    Ivy.log().info("Chart dataset {0}", dataset.values());
    return dataset;
  }

  private static void buildCustomFieldValueQuery(ChartFilter chartFilter, BoolQueryBuilder boolQuery) {
    if (CollectionUtils.isNotEmpty(chartFilter.getCustomfieldValue())) {
      if (ColumnType.CUSTOM_STRING == chartFilter.getxAxisData().getColumnType()) {
        var customfieldQuery = "";
        for (var customvalue : chartFilter.getCustomfieldValue()) {
          customfieldQuery = customfieldQuery.isEmpty() ? customfieldQuery.concat(customvalue)
              : customfieldQuery.concat(QUERY_STRING_OR).concat(customvalue);
        }
        var stringQueryBuilder = new QueryStringQueryBuilder(customfieldQuery);
        stringQueryBuilder.defaultField(CUSTOM_STRING_VALUE);
        boolQuery.must().add(stringQueryBuilder);
      }
    }
  }

  private static void buildCustomFieldQuery(ChartFilter filter, BoolQueryBuilder boolQuery) {
    if (ColumnType.CUSTOM_STRING == filter.getxAxisData().getColumnType()) {
      boolQuery.must().add(QueryBuilders.matchQuery(CUSTOM_STRING_NAME, filter.getxAxisData().getColumnName()));
    }
  }

  private static void buildCategoryQuery(ChartFilter filter, BoolQueryBuilder boolQuery) {
    if (CollectionUtils.isNotEmpty(filter.getCategories())) {
      var categoryQuery = "";
      for (var category : filter.getCategories()) {
        if (StringUtils.contains(category, "/")) {
          var newPath = "";
          for (var path : category.split("/")) {
            newPath = newPath.isEmpty() ? path : newPath.concat("\\/").concat(path);
          }
          category = newPath;
        }

        categoryQuery = categoryQuery.isEmpty() ? categoryQuery.concat(category)
            : categoryQuery.concat(QUERY_STRING_OR).concat(category);
      }
      var stringQueryBuilder = new QueryStringQueryBuilder(categoryQuery);
      stringQueryBuilder.defaultField(CATEGORY);
      boolQuery.must().add(stringQueryBuilder);
    }
  }

  private static void buildStateQuery(ChartFilter filter, BoolQueryBuilder boolQuery) {
    if (CollectionUtils.isNotEmpty(filter.getStates())) {
      boolQuery.filter(QueryBuilders.termsQuery(STATE, filter.getStates()));
    }
  }

  private static void buildAppQuery(BoolQueryBuilder boolQuery) {
    boolQuery.must().add(QueryBuilders.matchQuery(APP_ID, Ivy.request().getApplication().getId()));
  }

  private static void buildPermissionQuery(ChartFilter filter, BoolQueryBuilder boolQuery) {
    if (filter.isCanWorkOn() || !PermissionUtils.hasPermission(IPermission.CASE_READ_ALL)) {
      boolQuery.must().add(QueryBuilders.matchQuery(CREATOR_ID, filter.getSecurityMemberId()));

      var activatorQuery = "";
      for (var roleId : filter.getRoleSecurityMemberIds()) {
        activatorQuery = activatorQuery.isEmpty() ? activatorQuery.concat(roleId)
            : activatorQuery.concat(QUERY_STRING_OR).concat(roleId);
      }
      var stringQueryBuilder = new QueryStringQueryBuilder(activatorQuery);
      stringQueryBuilder.defaultField(ACTIVATOR_ID);
      boolQuery.must().add(stringQueryBuilder);
    }
  }

  private static void buildIncludeFields(ChartFilter filter, SearchCriteria criteria, String... additionalField) {
    var xArisData = filter.getxAxisData();
    List<String> includeFields = getCommonColumns();
    if (additionalField != null) {
      includeFields.addAll(Arrays.asList(additionalField));
    }

    if (DataType.TASK == filter.getDataType()) {
      includeFields.add(TASK_ID);
    }

    includeFields.add(xArisData.getColumnName());

    switch (xArisData.getColumnType()) {
      case CUSTOM_NUMBER:
        includeFields.addAll(Arrays.asList(CUSTOM_NUMBER_NAME, CUSTOM_NUMBER_VALUE));
        break;
      case CUSTOM_STRING:
        includeFields.addAll(Arrays.asList(CUSTOM_STRING_NAME, CUSTOM_STRING_VALUE));
        break;
      case CUSTOM_TEXT:
        includeFields.addAll(Arrays.asList(CUSTOM_TEXT_NAME, CUSTOM_TEXT_VALUE));
        break;
      case CUSTOM_TIMESTAMP:
        includeFields.addAll(Arrays.asList(CUSTOM_TIMESTAMP_NAME, CUSTOM_TIMESTAMP_VALUE));
        break;
      default:
        break;
    }
    criteria.setIncludeFields(includeFields.toArray(new String[includeFields.size()]));
  }

  private static ArrayList<String> getCommonColumns() {
    return new ArrayList<>(Arrays.asList(APP_ID, ID, CASE_ID, BUSINESS_CASE_ID, BUSINESS_RUNTIME, STATE));
  }
}
