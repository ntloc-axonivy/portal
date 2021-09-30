package ch.ivy.addon.portalkit.statistics.es.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

public class SearchCriteria implements Serializable {

  private static final long serialVersionUID = 2148826788922303512L;
  private String indexName;
  private String[] includeFields = Strings.EMPTY_ARRAY;
  private String[] excludeFields = Strings.EMPTY_ARRAY;
  private String sortField = "_id";
  private SortOrder sortOrder = SortOrder.DESC;
  private int searchFrom = 0;
  private int searchSize = 1000;
  private List<QueryBuilder> filters = new ArrayList<>();
  private QueryBuilder query;

  public SearchCriteria() {}

  public SearchCriteria(String indexName) {
    this.indexName = indexName;
  }

  public String getIndexName() {
    return indexName;
  }

  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }

  public String[] getIncludeFields() {
    return includeFields;
  }

  public void setIncludeFields(String[] includeFields) {
    this.includeFields = includeFields;
  }

  public String[] getExcludeFields() {
    return excludeFields;
  }

  public void setExcludeFields(String[] excludeFields) {
    this.excludeFields = excludeFields;
  }

  public String getSortField() {
    return sortField;
  }

  public void setSortField(String sortField) {
    this.sortField = sortField;
  }

  public SortOrder getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(SortOrder sortOrder) {
    this.sortOrder = sortOrder;
  }

  public int getSearchFrom() {
    return searchFrom;
  }

  public void setSearchFrom(int searchFrom) {
    this.searchFrom = searchFrom;
  }

  public int getSearchSize() {
    return searchSize;
  }

  public void setSearchSize(int searchSize) {
    this.searchSize = searchSize;
  }

  public List<QueryBuilder> getFilters() {
    return filters;
  }

  public void setFilters(List<QueryBuilder> filters) {
    this.filters = filters;
  }

  public QueryBuilder getQuery() {
    return query;
  }

  public void setQuery(QueryBuilder query) {
    this.query = query;
  }
}
