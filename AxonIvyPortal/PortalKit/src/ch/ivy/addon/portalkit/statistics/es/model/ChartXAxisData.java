package ch.ivy.addon.portalkit.statistics.es.model;

import java.io.Serializable;

import ch.ivy.addon.portalkit.statistics.es.enums.ColumnType;

public class ChartXAxisData implements Serializable {

  private static final long serialVersionUID = 4820515187453845531L;
  private ColumnType columnType;
  private String columnName;
  private String displayName;

  public ChartXAxisData() {}

  public ChartXAxisData(ColumnType columnType, String columnName, String displayName) {
    this.columnType = columnType;
    this.columnName = columnName;
    this.displayName = displayName;
  }
  
  public ChartXAxisData(ColumnType columnType, String columnName) {
    this.columnType = columnType;
    this.columnName = columnName;
    if (columnType != null) {
      this.displayName = String.format("%s (%s)", columnName, columnType.getDisplayName());
    } else {
      this.displayName = columnName;
    }
  }

  public ColumnType getColumnType() {
    return columnType;
  }

  public void setColumnType(ColumnType columnType) {
    this.columnType = columnType;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
}
