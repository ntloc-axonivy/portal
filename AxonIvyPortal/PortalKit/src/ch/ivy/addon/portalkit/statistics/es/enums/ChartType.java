package ch.ivy.addon.portalkit.statistics.es.enums;

public enum ChartType {
  BAR_CHART("bar", "Bar chart", "si si-analytics-bars"),
  PIE_CHART("pie", "Pie chart", "si si-analytics-pie-2"),
  LINE_CHART("line", "Line Chart - coming soon", "si si-analytics-board-graph-line"),
  DONUT_CHART("doughnut", "Donut chart - coming soon", "si si-cd");

  private String type;
  private String name;
  private String icon;

  private ChartType(String type, String name) {
    this.type = type;
    this.name = name;
  }

  private ChartType(String type, String name, String icon) {
    this.type = type;
    this.name = name;
    this.icon = icon;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }
}
