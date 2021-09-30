package ch.ivy.addon.portalkit.statistics.es.enums;

public enum DataType {

  CASE("portal_case", "Portal cases"), TASK("portal_task", "Portal tasks"),
  BUSINESS_DATA("portal_businessdata", "Portal business data");

  private String type;
  private String name;

  private DataType(String type, String name) {
    this.type = type;
    this.name = name;
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
}
