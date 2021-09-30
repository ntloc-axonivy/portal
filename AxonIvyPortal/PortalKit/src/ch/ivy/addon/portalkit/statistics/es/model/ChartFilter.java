package ch.ivy.addon.portalkit.statistics.es.model;

import java.util.List;

import ch.ivy.addon.portalkit.statistics.es.enums.ChartYAxisType;
import ch.ivy.addon.portalkit.statistics.es.enums.DataType;

public class ChartFilter {

  private DataType dataType;
  private ChartXAxisData xAxisData;
  private ChartYAxisType yAxisData;
  private boolean canWorkOn;
  private String securityMemberId;
  private List<String> roleSecurityMemberIds;
  private List<Integer> states;
  private List<String> categories;
  private List<String> customfieldValue;

  public ChartFilter() {}

  public ChartFilter(DataType dataType, ChartXAxisData xAxisData, ChartYAxisType yAxisData) {
    super();
    this.dataType = dataType;
    this.xAxisData = xAxisData;
    this.yAxisData = yAxisData;
  }

  public DataType getDataType() {
    return dataType;
  }

  public void setDataType(DataType dataType) {
    this.dataType = dataType;
  }

  public ChartXAxisData getxAxisData() {
    return xAxisData;
  }

  public void setxAxisData(ChartXAxisData xAxisData) {
    this.xAxisData = xAxisData;
  }

  public ChartYAxisType getyAxisData() {
    return yAxisData;
  }

  public void setyAxisData(ChartYAxisType yAxisData) {
    this.yAxisData = yAxisData;
  }

  public boolean isCanWorkOn() {
    return canWorkOn;
  }

  public void setCanWorkOn(boolean canWorkOn) {
    this.canWorkOn = canWorkOn;
  }

  public String getSecurityMemberId() {
    return securityMemberId;
  }

  public void setSecurityMemberId(String securityMemberId) {
    this.securityMemberId = securityMemberId;
  }

  public List<String> getRoleSecurityMemberIds() {
    return roleSecurityMemberIds;
  }

  public void setRoleSecurityMemberIds(List<String> roleSecurityMemberIds) {
    this.roleSecurityMemberIds = roleSecurityMemberIds;
  }

  public List<Integer> getStates() {
    return states;
  }

  public void setStates(List<Integer> states) {
    this.states = states;
  }

  public List<String> getCategories() {
    return categories;
  }

  public void setCategories(List<String> categories) {
    this.categories = categories;
  }

  public List<String> getCustomfieldValue() {
    return customfieldValue;
  }

  public void setCustomfieldValue(List<String> customfieldValue) {
    this.customfieldValue = customfieldValue;
  }
}
