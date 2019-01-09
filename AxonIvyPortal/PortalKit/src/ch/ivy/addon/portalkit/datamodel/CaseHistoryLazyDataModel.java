package ch.ivy.addon.portalkit.datamodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import ch.ivy.addon.portalkit.constant.PortalConstants;
import ch.ivy.addon.portalkit.enums.AdditionalProperty;
import ch.ivy.addon.portalkit.enums.CaseSortField;
import ch.ivy.addon.portalkit.ivydata.searchcriteria.CaseSearchCriteria;
import ch.ivy.addon.portalkit.service.IvyAdapterService;
import ch.ivy.addon.portalkit.util.PermissionUtils;
import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.workflow.CaseState;
import ch.ivyteam.ivy.workflow.ICase;
import ch.ivyteam.ivy.workflow.query.CaseQuery;

public class CaseHistoryLazyDataModel extends LazyDataModel<ICase> {

  private static final long serialVersionUID = 3023805225538732101L;

  private String businessEntityId;
  private final List<ICase> data;
  private CaseSearchCriteria searchCriteria;

  public CaseHistoryLazyDataModel(String businessEntityId) {
    super();
    this.businessEntityId = businessEntityId;
    data = new ArrayList<>();
    searchCriteria = buildInitSearchCriteria();
    setAdminQuery(PermissionUtils.checkReadAllCasesPermission());
    autoInitForNoAppConfiguration();
  }

  protected void autoInitForNoAppConfiguration() {
    String applicationName = StringUtils.EMPTY;
    String applicationNameFromRequest =
        Optional.ofNullable(Ivy.request().getApplication()).map(IApplication::getName).orElse(StringUtils.EMPTY);
    if (!PortalConstants.PORTAL_APPLICATION_NAME.equals(applicationNameFromRequest)) {
      applicationName = applicationNameFromRequest;
    }
    if (StringUtils.isNotBlank(applicationName)) {
      searchCriteria.setApps(Arrays.asList(applicationName));
    }
  }

  public void setAdminQuery(boolean isAdminQuery) {
    searchCriteria.setAdminQuery(isAdminQuery);
    if (isAdminQuery && !searchCriteria.getIncludedStates().contains(CaseState.DONE)) {
      searchCriteria.addIncludedStates(Arrays.asList(CaseState.DONE));
    }
  }

  private CaseSearchCriteria buildInitSearchCriteria() {
    CaseSearchCriteria crit = new CaseSearchCriteria();
    crit.setInvolvedUsername(Ivy.session().getSessionUserName());
    crit.setBusinessCase(true);
    crit.setIncludedStates(new ArrayList<>(Arrays.asList(CaseState.CREATED, CaseState.RUNNING, CaseState.DONE)));
    crit.setSortField(CaseSortField.ID.toString());
    crit.setSortDescending(true);
    return crit;
  }

  @Override
  public List<ICase> load(int first, int pageSize, String sortField, SortOrder sortOrder,
      Map<String, Object> filters) {
    if (first == 0) {
      initializedDataModel(searchCriteria);
    }

    List<ICase> foundCases = findCases(searchCriteria, first, pageSize);
    return foundCases;
  }

  @SuppressWarnings("unchecked")
  private List<ICase> findCases(CaseSearchCriteria criteria, int first, int pageSize) {
    int startIndex = first;
    int count = pageSize;
    if (startIndex < 0) {
      startIndex = 0;
      count = first + pageSize;
    }
    Map<String, Object> params = new HashMap<>();
    params.put("startIndex", startIndex);
    params.put("count", count);
    params.put("caseSearchCriteria", criteria);
    Map<String, Object> response =
        IvyAdapterService.startSubProcess(
            "findCasesByCriteria(ch.ivy.ws.addon.CaseSearchCriteria,Integer,Integer)", params, new ArrayList<>());
    return (List<ICase>) response.get("cases");
  }

  public void setSorting(String sortedField, boolean descending) {
    searchCriteria.setSortField(sortedField);
    searchCriteria.setSortDescending(descending);
  }

  public String getSortField() {
    return searchCriteria.getSortField();
  }

  public boolean isSortDescending() {
    return searchCriteria.isSortDescending();
  }

  private int getCaseCount(CaseSearchCriteria criteria) {
    Map<String, Object> params = new HashMap<>();
    params.put("caseSearchCriteria", criteria);
    Map<String, Object> response =
        IvyAdapterService.startSubProcess("countCasesByCriteria(ch.ivy.ws.addon.CaseSearchCriteria)", params,
            new ArrayList<>());
    return ((Long) response.get("caseCount")).intValue();
  }

  private void initializedDataModel(CaseSearchCriteria criteria) {
    data.clear();
    buildQueryToSearchCriteria();
    setRowCount(getCaseCount(criteria));
  }

  private void buildQueryToSearchCriteria() {
    if (searchCriteria.getCustomCaseQuery() == null) {
      CaseQuery caseQuery = CaseQuery.create();
      caseQuery.where().additionalProperty(AdditionalProperty.CASE_BUSINESS_ENTITY_PROPERTY.toString())
          .isEqual(businessEntityId);
      searchCriteria.setCustomCaseQuery(caseQuery);
    }
  }

  public String getBusinessEntityId() {
    return businessEntityId;
  }

  public void setBusinessEntityId(String businessEntityId) {
    this.businessEntityId = businessEntityId;
  }
  
  public boolean isSelectedColumn(String column) {
    return StringUtils.isNotEmpty(column);
  }
  
  public boolean isAutoHideColumns() {
    return false;
  }
}
