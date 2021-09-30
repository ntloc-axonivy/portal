package ch.ivy.addon.portalkit.statistics.es.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import ch.ivy.addon.portalkit.statistics.es.enums.DataType;
import ch.ivy.addon.portalkit.statistics.es.search.SearchCriteria;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.workflow.TaskState;

public class ChartService extends ESRestHighClientService {

  private static ChartService instance;

  public static ChartService getInstance() {
    if (instance == null) {
      instance = new ChartService();
    }
    return instance;
  }

  public static List<String> findAllValuesOfColumn(SearchCriteria searchCriteria) {
    var hits = searchByCriteria(openESClient(), searchCriteria);
    Set<String> valuesOfColumn = new HashSet<>();
    hits.forEach(hit -> {
      valuesOfColumn.add(String.valueOf(hit.getSourceAsMap().get(searchCriteria.getIncludeFields()[0])));
    });

    return new ArrayList<>(valuesOfColumn);
  }

  public static List<String> findAllColumns(String indexName) {
    var searchCriteria = new SearchCriteria();
    searchCriteria.setSearchSize(1);
    searchCriteria.setIndexName(indexName);
    searchCriteria.setIncludeFields(Strings.EMPTY_ARRAY);
    searchCriteria.setExcludeFields(new String[] {"_index", "_type", "_score", "@*", "custom*"});
    searchCriteria.setQuery(QueryBuilders.matchAllQuery());
    var searchHits = searchByCriteria(openESClient(), searchCriteria);
    var result = new HashSet<String>();
    for (SearchHit hit : searchHits) {
      Ivy.log().info("** Found hit {0}", hit.getId());
      result.addAll(hit.getSourceAsMap().keySet());
    }
    return new ArrayList<>(result);
  }

  public static List<SearchHit> findByCriteria(SearchCriteria searchCriteria) {
    return searchByCriteria(openESClient(), searchCriteria);
  }

  private static RestHighLevelClient openESClient() {
    return createHighRestClient(9200);
  }

  public static Map<TaskState, Integer> findTaskByStates() {
    SearchCriteria criteria = new SearchCriteria();
    criteria.setIndexName(DataType.TASK.getType());
    var tasksByState = new HashMap<TaskState, Integer>();
    var searchHits = searchByCriteria(openESClient(), criteria);
    for (SearchHit hit : searchHits) {
      Ivy.log().info("** Test search hit {0}", hit.getSourceAsString());
      Ivy.log().info("** Test search hit keyset {0}", hit.getSourceAsMap().keySet());
      var sourceMap = hit.getSourceAsMap();
      var state = Integer.valueOf(sourceMap.get("state").toString());
      var taskState = TaskState.valueOf(state.intValue());
      int totalTask = 1;
      if (tasksByState.containsKey(taskState)) {
        totalTask = tasksByState.get(taskState) + 1;
      }
      tasksByState.put(taskState, totalTask);
    }
    return tasksByState;
  }

  public static Map<String, Integer> findCasesByCreationTime() {
    var searchCriteria = new SearchCriteria();
    searchCriteria.setIndexName(DataType.CASE.getType());
    searchCriteria.setSortField("caseid");
    searchCriteria.setIncludeFields(Strings.EMPTY_ARRAY);
    searchCriteria.setExcludeFields(new String[] {"_index", "_type", "@version", "applicationid"});
    searchCriteria.setQuery(QueryBuilders.matchQuery("applicationid", Ivy.request().getApplication().getId()));

    var resutl = searchByCriteria(openESClient(), searchCriteria);

    var caseByCreationTime = new HashMap<String, Integer>();
    for (SearchHit hit : resutl) {
      var starttimestamp = hit.getSourceAsMap().get("starttimestamp").toString();
      var totalCases = Integer.valueOf(1);
      if (caseByCreationTime.containsKey(starttimestamp)) {
        totalCases = caseByCreationTime.get(starttimestamp) + 1;
      }
      caseByCreationTime.put(starttimestamp, totalCases);
    }
    return caseByCreationTime;
  }

  public static Map<String, SearchHit> findPortalCasesByCriteria(SearchCriteria searchCriteria) {
    var result = new HashMap<String, SearchHit>();
    var queryBool = QueryBuilders.boolQuery();
    queryBool.must(QueryBuilders.matchQuery("applicationid", Ivy.request().getApplication().getId()));
    searchCriteria.getFilters().forEach(filter -> {
      queryBool.filter(filter);
    });
    searchCriteria.setQuery(queryBool);
    Ivy.log().info("Query {0}", queryBool.toString());

    var searchHits = searchByCriteria(openESClient(), searchCriteria);
    for (SearchHit hit : searchHits) {
      Ivy.log().info("** Found hit {0}", hit.getId());
      Ivy.log().info("** Found hit source {0}", hit.getSourceAsString());
      result.put(hit.getId(), hit);
    }
    Ivy.log().info("** Resutl size {0}", result.size());
    return result;
  }
}
