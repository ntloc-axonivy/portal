package com.axonivy.portal.enums.dashboard.filter;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import ch.ivyteam.ivy.environment.Ivy;

public enum FilterOperator {
  TODAY,
  YESTERDAY,
  IS,
  IS_NOT,
  BEFORE,
  AFTER,
  BETWEEN,
  NOT_BETWEEN,
  CURRENT,
  LAST,
  NEXT,
  EMPTY,
  NOT_EMPTY,
  CONTAINS,
  NOT_CONTAINS,
  IN,
  NOT_IN,
  START_WITH,
  NOT_START_WITH,
  END_WITH,
  NOT_END_WITH,
  CURRENT_USER_CAN_WORK_ON,
  CURRENT_USER,
  NO_CATEGORY;

  public String getLabel() {
    return Ivy.cms().co(String.format("/Labels/Enums/FilterOperator/%s", this.name()));
  }

  public static final Set<FilterOperator> DATE_OPERATORS = Collections.unmodifiableSet(EnumSet.of(TODAY, YESTERDAY, IS, IS_NOT, BEFORE, AFTER, BETWEEN, NOT_BETWEEN, CURRENT, LAST, NEXT, EMPTY, NOT_EMPTY));
  public static final Set<FilterOperator> CREATED_DATE_OPERATORS = Collections.unmodifiableSet(EnumSet.of(TODAY, YESTERDAY, IS, IS_NOT, BEFORE, AFTER, BETWEEN, NOT_BETWEEN, CURRENT, LAST, NEXT));
  public static final Set<FilterOperator> STATE_OPERATORS = Collections.unmodifiableSet(EnumSet.of(IN));
  public static final Set<FilterOperator> CREATOR_OPERATORS = Collections.unmodifiableSet(EnumSet.of(IN, NOT_IN, CURRENT_USER));
  public static final Set<FilterOperator> CATEGORY_OPERATORS = Collections.unmodifiableSet(EnumSet.of(IN, NOT_IN, CONTAINS, NOT_CONTAINS, NO_CATEGORY));
  public static final Set<FilterOperator> APPLICATION_OPERATORS = Collections.unmodifiableSet(EnumSet.of(IN));
}
