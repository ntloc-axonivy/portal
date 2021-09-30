package ch.ivy.addon.portalkit.statistics.es.enums;

public enum ColumnType {
  STANDARD("Standard"), CUSTOM_STRING("Custom string"), CUSTOM_NUMBER("Custom number"),
  CUSTOM_TEXT("Custom text"), CUSTOM_TIMESTAMP("Custom timestamp");

  private String displayName;

  private ColumnType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
}
