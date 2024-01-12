package com.axonivy.portal.developerexamples.enums;

import ch.ivyteam.ivy.environment.Ivy;

public enum StatisticTimePeriodSelection {
  CUSTOM("custom"),
  LAST_WEEK("lastWeek"),
  LAST_MONTH("lastMonth"),
  LAST_6_MONTH("last6Month");
  
private final String label;
  
  StatisticTimePeriodSelection(String label) {
    this.label = label;
  }
  
  public String getLabel() {
    return Ivy.cms().co("/Labels/statistic/timePeriod/" + label);
  }
}
