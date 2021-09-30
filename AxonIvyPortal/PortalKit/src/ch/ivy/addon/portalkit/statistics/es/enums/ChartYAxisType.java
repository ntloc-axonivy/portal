package ch.ivy.addon.portalkit.statistics.es.enums;

public enum ChartYAxisType {
  CASES("Number of cases"), TASKS("Number of tasks"), TIME_HOURS("Time in hours");//, TIME_MIN("Time in minutes");

  private String name;

  private ChartYAxisType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}
