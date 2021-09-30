package ch.ivy.addon.portalkit.statistics.es.managedbean;

import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CATEGORY;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CUSTOM_NUMBER_NAME;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CUSTOM_STRING_NAME;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CUSTOM_STRING_VALUE;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CUSTOM_TEXT_NAME;
import static ch.ivy.addon.portalkit.statistics.es.constant.ChartConstants.CUSTOM_TIMESTAMP_NAME;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.charts.ChartModel;

import ch.ivy.addon.portalkit.statistics.es.enums.ChartType;
import ch.ivy.addon.portalkit.statistics.es.enums.ChartYAxisType;
import ch.ivy.addon.portalkit.statistics.es.enums.ColumnType;
import ch.ivy.addon.portalkit.statistics.es.enums.DataType;
import ch.ivy.addon.portalkit.statistics.es.model.ChartFilter;
import ch.ivy.addon.portalkit.statistics.es.model.ChartXAxisData;
import ch.ivy.addon.portalkit.statistics.es.search.SearchCriteria;
import ch.ivy.addon.portalkit.statistics.es.service.ChartService;
import ch.ivy.addon.portalkit.statistics.es.util.ChartESUtil;
import ch.ivy.addon.portalkit.statistics.es.util.ChartJsUtil;
import ch.ivy.addon.portalkit.util.TaskTreeUtils;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.workflow.CaseState;
import ch.ivyteam.ivy.workflow.TaskState;

@ManagedBean
@ViewScoped
public class ChartJsConfigBean implements Serializable {

  private static final long serialVersionUID = -7416590681836314912L;

  private ChartType chartType;
  private List<ChartType> chartTypeSelection = Arrays.asList(ChartType.values());
  private String chartName;
  private String xAxisName;
  private ChartXAxisData xAxisData;
  private List<ChartXAxisData> xAxisDataSelection = new ArrayList<>();
  private String yAxisName;
  private ChartYAxisType yAxisData;
  private List<ChartYAxisType> yAxisDataSelection = new ArrayList<>();
  private DataType dataType;
  private List<DataType> dataTypeSelection = Arrays.asList(DataType.values());
  private List<String> selectedCustomfieldData;
  private List<String> customfieldDataSelection;
  private List<TaskState> taskStatesFilter;
  private List<TaskState> selectedTaskStatesFilter;
  private List<CaseState> caseStatesFilter;
  private List<CaseState> selectedCaseStatesFilter;
  private List<String> categoriesSelection;
  private List<String> selectedCategories;
  private String selectedColumn;
  private List<String> availableColumns = new ArrayList<>();
  private Boolean canWorkOn;

  private String sessionSecurityMemberId;
  private List<String> sessionRolesSecurityMemberIds;

  private ChartModel chartModel;

  @PostConstruct
  public void init() {
    chartModel = null;
    sessionRolesSecurityMemberIds = new ArrayList<>();
    var sessionUser = Ivy.session().getSessionUser();
    sessionSecurityMemberId = sessionUser.getSecurityMemberId();
    sessionUser.getAllRoles().forEach(role -> {
      sessionRolesSecurityMemberIds.add(role.getSecurityMemberId());
    });
  }

  public List<ChartXAxisData> completeXAxisDataMethod(String query) {
    var searchList = xAxisDataSelection.stream().filter(xdata -> xdata.getColumnName().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
    searchList.add(new ChartXAxisData(null, "select"));
    Ivy.log().info("X-aris data size {0}", searchList.size());
    return searchList;
  }

  public void onSelectChartType() {
    Ivy.log().info("On select chart type {0}", chartType);
  }

  public void onSelectPieColumn() {
    if (this.selectedColumn == null) {
      return;
    }
    SearchCriteria criteria = new SearchCriteria();
    criteria.setIndexName(dataType.getType());
    criteria.setIncludeFields(new String[] {selectedColumn});
    ChartService.findAllValuesOfColumn(criteria);
  }

  public void addNewPiece() {
    Ivy.log().info("Add new piece");
  }

  public void onReview() {
    if (chartType == null) {
      return;
    }
    Map<String, Number> dataset = new HashMap<>();
    switch (dataType) {
      case CASE:
        dataset = createCaseDataset();
        break;
      case TASK:
        dataset = createTaskDataset();
        break;
      case BUSINESS_DATA:
        break;
      default:
        break;
    }

    switch (chartType) {
      case BAR_CHART:
        this.chartModel = ChartJsUtil.createBarModel(chartName, xAxisName, yAxisName, dataset);
        break;
      case PIE_CHART:
        this.chartModel = ChartJsUtil.createPieModel(dataset);
        break;
      default:
        break;
    }
  }

  private Map<String, Number> createTaskDataset() {
    var chartFilter = new ChartFilter(dataType, xAxisData, yAxisData);
    chartFilter.setCanWorkOn(canWorkOn);
    chartFilter.setCategories(selectedCategories);
    chartFilter.setCustomfieldValue(selectedCustomfieldData);
    chartFilter.setSecurityMemberId(sessionSecurityMemberId);
    chartFilter.setRoleSecurityMemberIds(sessionRolesSecurityMemberIds);
    chartFilter.setStates(CollectionUtils.emptyIfNull(selectedTaskStatesFilter).stream().map(TaskState::intValue).collect(Collectors.toList()));
    return ChartESUtil.createChartDataset(chartFilter);
  }

  private Map<String, Number> createCaseDataset() {
    var chartFilter = new ChartFilter(dataType, xAxisData, yAxisData);
    chartFilter.setCanWorkOn(canWorkOn);
    chartFilter.setCategories(selectedCategories);
    chartFilter.setCustomfieldValue(selectedCustomfieldData);
    chartFilter.setSecurityMemberId(sessionSecurityMemberId);
    chartFilter.setRoleSecurityMemberIds(sessionRolesSecurityMemberIds);
    chartFilter.setStates(CollectionUtils.emptyIfNull(selectedCaseStatesFilter).stream().map(CaseState::intValue).collect(Collectors.toList()));
    return ChartESUtil.createChartDataset(chartFilter);
  }

  public void onSelectChartDataType() {
    if (dataType == null) {
      return;
    }

    var availableColumns = ChartService.findAllColumns(dataType.getType());
    this.xAxisDataSelection = availableColumns.stream()
        .map(column -> new ChartXAxisData(ColumnType.STANDARD, column)).collect(Collectors.toList());
    this.availableColumns = availableColumns;

    switch (dataType) {
      case CASE:
        caseStatesFilter = Arrays.asList(CaseState.values());
        buildCategoryTree(dataType);
        this.xAxisDataSelection.addAll(findCustomfieldData(dataType));
        this.yAxisDataSelection = Arrays.asList(ChartYAxisType.CASES, ChartYAxisType.TIME_HOURS);
        break;
      case TASK:
        taskStatesFilter = Arrays.asList(TaskState.values());
        buildCategoryTree(dataType);
        this.xAxisDataSelection.addAll(findCustomfieldData(dataType));
        this.yAxisDataSelection = Arrays.asList(ChartYAxisType.TASKS, ChartYAxisType.TIME_HOURS);
        break;
      case BUSINESS_DATA:
        break;
      default:
        break;
    }
  }

  private List<ChartXAxisData> findCustomfieldData(DataType dataType) {
    var found = new HashSet<String>();
    SearchCriteria criteria = new SearchCriteria(dataType.getType());
    criteria.setQuery(QueryBuilders.matchAllQuery());
    criteria.setIncludeFields(new String[] {CUSTOM_STRING_NAME});
    found.addAll(ChartService.findAllValuesOfColumn(criteria));

    criteria.setIncludeFields(new String[] {CUSTOM_NUMBER_NAME});
    found.addAll(ChartService.findAllValuesOfColumn(criteria));

    criteria.setIncludeFields(new String[] {CUSTOM_TEXT_NAME});
    found.addAll(ChartService.findAllValuesOfColumn(criteria));

    criteria.setIncludeFields(new String[] {CUSTOM_TIMESTAMP_NAME});
    found.addAll(ChartService.findAllValuesOfColumn(criteria));

    var result = found.stream().filter(data -> !Objects.isNull(data)).filter(data -> !StringUtils.equals(data, null))
        .map(customname -> new ChartXAxisData(ColumnType.CUSTOM_STRING, customname)).collect(Collectors.toList());
    return result;
  }

  private void buildCategoryTree(DataType dataType) {
    SearchCriteria criteria = new SearchCriteria(dataType.getType());
    criteria.setIncludeFields(new String[] {CATEGORY});
    criteria.setQuery(QueryBuilders.matchAllQuery());
    categoriesSelection = ChartService.findAllValuesOfColumn(criteria);
    Ivy.log().info("Categories found {0}", categoriesSelection.toArray());
  }

  public void onSelectXAris() {
    if (this.xAxisData != null) {
      SearchCriteria criteria = new SearchCriteria(dataType.getType());
      criteria.setIncludeFields(new String[] {CUSTOM_STRING_VALUE});
      criteria.setQuery(QueryBuilders.matchQuery(CUSTOM_STRING_NAME, xAxisData.getColumnName()));
      customfieldDataSelection = ChartService.findAllValuesOfColumn(criteria);
    }
  }

  public void onSelectYAris() {
    Ivy.log().info("On select Y Aris {0}", this.yAxisData);
  }

  public void itemSelect(ItemSelectEvent event) {
    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
        "Item Index: " + event.getItemIndex() + ", DataSet Index:" + event.getDataSetIndex());

    FacesContext.getCurrentInstance().addMessage(null, msg);
  }

  public void buildCategoryTree() {
    var categoryTree = TaskTreeUtils.buildTaskCategoryCheckboxTreeRoot();
    categoriesSelection = categoryTree.getChildren().stream().map(TreeNode::getRowKey).collect(Collectors.toList());
  }

  public ChartType getChartType() {
    return chartType;
  }

  public void setChartType(ChartType chartType) {
    this.chartType = chartType;
  }

  public List<ChartType> getChartTypeSelection() {
    return chartTypeSelection;
  }

  public void setChartTypeSelection(List<ChartType> chartTypeSelection) {
    this.chartTypeSelection = chartTypeSelection;
  }

  public String getChartName() {
    return chartName;
  }

  public void setChartName(String chartName) {
    this.chartName = chartName;
  }

  public String getxAxisName() {
    return xAxisName;
  }

  public void setxAxisName(String xAxisName) {
    this.xAxisName = xAxisName;
  }

  public ChartXAxisData getxAxisData() {
    return xAxisData;
  }

  public void setxAxisData(ChartXAxisData xAxisData) {
    this.xAxisData = xAxisData;
  }

  public List<ChartXAxisData> getxAxisDataSelection() {
    return xAxisDataSelection;
  }

  public void setxAxisDataSelection(List<ChartXAxisData> xAxisDataSelection) {
    this.xAxisDataSelection = xAxisDataSelection;
  }

  public String getyAxisName() {
    return yAxisName;
  }

  public void setyAxisName(String yAxisName) {
    this.yAxisName = yAxisName;
  }

  public ChartYAxisType getyAxisData() {
    return yAxisData;
  }

  public void setyAxisData(ChartYAxisType yAxisData) {
    this.yAxisData = yAxisData;
  }

  public List<ChartYAxisType> getyAxisDataSelection() {
    return yAxisDataSelection;
  }

  public void setyAxisDataSelection(List<ChartYAxisType> yAxisDataSelection) {
    this.yAxisDataSelection = yAxisDataSelection;
  }

  public DataType getDataType() {
    return dataType;
  }

  public void setDataType(DataType dataType) {
    this.dataType = dataType;
  }

  public List<DataType> getDataTypeSelection() {
    return dataTypeSelection;
  }

  public void setDataTypeSelection(List<DataType> dataTypeSelection) {
    this.dataTypeSelection = dataTypeSelection;
  }

  public List<String> getSelectedCustomfieldData() {
    return selectedCustomfieldData;
  }

  public void setSelectedCustomfieldData(List<String> selectedCustomfieldData) {
    this.selectedCustomfieldData = selectedCustomfieldData;
  }

  public List<String> getCustomfieldDataSelection() {
    return customfieldDataSelection;
  }

  public void setCustomfieldDataSelection(List<String> customfieldDataSelection) {
    this.customfieldDataSelection = customfieldDataSelection;
  }

  public List<TaskState> getTaskStatesFilter() {
    return taskStatesFilter;
  }

  public void setTaskStatesFilter(List<TaskState> taskStatesFilter) {
    this.taskStatesFilter = taskStatesFilter;
  }

  public List<TaskState> getSelectedTaskStatesFilter() {
    return selectedTaskStatesFilter;
  }

  public void setSelectedTaskStatesFilter(List<TaskState> selectedTaskStatesFilter) {
    this.selectedTaskStatesFilter = selectedTaskStatesFilter;
  }

  public List<CaseState> getCaseStatesFilter() {
    return caseStatesFilter;
  }

  public void setCaseStatesFilter(List<CaseState> caseStatesFilter) {
    this.caseStatesFilter = caseStatesFilter;
  }

  public List<CaseState> getSelectedCaseStatesFilter() {
    return selectedCaseStatesFilter;
  }

  public void setSelectedCaseStatesFilter(List<CaseState> selectedCaseStatesFilter) {
    this.selectedCaseStatesFilter = selectedCaseStatesFilter;
  }

  public List<String> getCategoriesSelection() {
    return categoriesSelection;
  }

  public void setCategoriesSelection(List<String> categoriesSelection) {
    this.categoriesSelection = categoriesSelection;
  }

  public List<String> getSelectedCategories() {
    return selectedCategories;
  }

  public void setSelectedCategories(List<String> selectedCategories) {
    this.selectedCategories = selectedCategories;
  }

  public String getSelectedColumn() {
    return selectedColumn;
  }

  public void setSelectedColumn(String selectedColumn) {
    this.selectedColumn = selectedColumn;
  }

  public List<String> getAvailableColumns() {
    return availableColumns;
  }

  public void setAvailableColumns(List<String> availableColumns) {
    this.availableColumns = availableColumns;
  }

  public ChartModel getChartModel() {
    return chartModel;
  }

  public void setChartModel(ChartModel chartModel) {
    this.chartModel = chartModel;
  }

  public Boolean getCanWorkOn() {
    return canWorkOn;
  }

  public void setCanWorkOn(Boolean canWorkOn) {
    this.canWorkOn = canWorkOn;
  }
}
