package ch.ivy.addon.portalkit.taskfilter;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.ivy.addon.portalkit.casefilter.CaseFilter;
import ch.ivy.addon.portalkit.filter.AbstractFilterData;

/**
 * @deprecated use TaskAnalysisFilterData in package ch.ivy.addon.portalkit.casefilter.impl
 *
 */
@Deprecated(since="8.0.18")
@SuppressWarnings("rawtypes")
public class TaskAnalysisFilterData extends AbstractFilterData {
  private List<TaskFilter> taskFilters = new ArrayList<>();
  private List<CaseFilter> caseFilters = new ArrayList<>();

  public List<TaskFilter> getTaskFilters() {
    return taskFilters;
  }

  public void setTaskFilters(List<TaskFilter> taskFilters) {
    this.taskFilters = taskFilters;
  }

  public List<CaseFilter> getCaseFilters() {
    return caseFilters;
  }

  public void setCaseFilters(List<CaseFilter> caseFilters) {
    this.caseFilters = caseFilters;
  }

  @Override
  public void setFilters(List filters) {
    throw new UnsupportedOperationException();
  }

  @Override
  @JsonIgnore
  public List getFilters() {
    throw new UnsupportedOperationException();
  }

  @Override
  @JsonIgnore
  public String getKeyword() {
    return super.getKeyword();
  }

  
}
