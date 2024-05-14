package com.axonivy.portal.util.filter.operator.caze.customfield;

import org.apache.commons.collections4.CollectionUtils;

import com.axonivy.portal.dto.dashboard.filter.DashboardFilter;

import ch.ivyteam.ivy.workflow.query.CaseQuery;

public class CustomTextEndWithOperatorHandler {

  private static final String END_WTIH_FORMAT = "%%%s";

  private static CustomTextEndWithOperatorHandler instance;

  public static CustomTextEndWithOperatorHandler getInstance() {
    if (instance == null) {
      instance = new CustomTextEndWithOperatorHandler();
    }
    return instance;
  }

  public CaseQuery buildEndWithQuery(DashboardFilter filter) {
    if (CollectionUtils.isEmpty(filter.getValues())) {
      return null;
    }
    CaseQuery query = CaseQuery.create();
    filter.getValues().forEach(text -> {
      CaseQuery subQuery = CaseQuery.create();
      subQuery.where().customField().textField(filter.getField())
          .isLikeIgnoreCase(String.format(END_WTIH_FORMAT, text.toLowerCase()));
      query.where().or(subQuery);
    });
    return query;
  }

  public CaseQuery buildNotEndWithQuery(DashboardFilter filter) {
    if (CollectionUtils.isEmpty(filter.getValues())) {
      return null;
    }
    CaseQuery query = CaseQuery.create();
    filter.getValues().forEach(text -> {
      CaseQuery subQuery = CaseQuery.create();
      subQuery.where().customField().textField(filter.getField())
          .isNotLikeIgnoreCase(String.format(END_WTIH_FORMAT, text.toLowerCase()));
      query.where().and(subQuery);
    });
    return query;
  }
}