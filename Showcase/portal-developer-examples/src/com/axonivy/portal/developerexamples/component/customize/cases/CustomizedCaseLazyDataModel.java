package com.axonivy.portal.developerexamples.component.customize.cases;

import java.util.Arrays;
import java.util.List;

import ch.ivy.addon.portalkit.constant.CustomFields;
import ch.ivy.addon.portalkit.datamodel.CaseLazyDataModel;
import ch.ivy.addon.portalkit.enums.CaseSortField;
import ch.ivy.addon.portalkit.service.GlobalSettingService;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.workflow.query.CaseQuery;

public class CustomizedCaseLazyDataModel extends CaseLazyDataModel {
  private static final long serialVersionUID = 1L;
	public static final String CUSTOM_TIMESTAMP_FIELD1 = "customTimestampField1";
	public static final String CUSTOM_VARCHAR_FIELD1 = "customVarCharField1";

	public CustomizedCaseLazyDataModel() {
		super();
	}

	public CustomizedCaseLazyDataModel(String caseWidgetComponentId) {
		super(caseWidgetComponentId);
	}

	// ===================Basic customization, extend columns====================

	// Extend sort fields, include 1 text field and 1 date time field
	@Override
	public void extendSort(CaseQuery caseQuery) {
		if (CUSTOM_VARCHAR_FIELD1.equalsIgnoreCase(criteria.getSortField())) {
			if (criteria.isSortDescending()) {
				caseQuery.orderBy().customField().stringField(CustomFields.CUSTOM_VARCHAR_FIELD1).descending();
			} else {
				caseQuery.orderBy().customField().stringField(CustomFields.CUSTOM_VARCHAR_FIELD1);
			}
		} else if (CUSTOM_TIMESTAMP_FIELD1.equalsIgnoreCase(criteria.getSortField())) {
			if (criteria.isSortDescending()) {
				caseQuery.orderBy().customField().timestampField(CustomFields.CUSTOM_TIMESTAMP_FIELD1).descending();
			} else {
				caseQuery.orderBy().customField().timestampField(CustomFields.CUSTOM_TIMESTAMP_FIELD1);
			}
		}
	}

	@Override
	public List<String> getDefaultColumns() {
    if (new GlobalSettingService().isCaseOwnerEnabled()) {
      return Arrays.asList(CaseSortField.NAME.name(), CaseSortField.ID.name(), CaseSortField.CREATOR.name(), CaseSortField.OWNER.name(), CaseSortField.CREATION_TIME.name(), CaseSortField.FINISHED_TIME.name(), CUSTOM_VARCHAR_FIELD1, CUSTOM_TIMESTAMP_FIELD1);
    } else {
      return Arrays.asList(CaseSortField.NAME.name(), CaseSortField.ID.name(), CaseSortField.CREATOR.name(), CaseSortField.CREATION_TIME.name(), CaseSortField.FINISHED_TIME.name(), CUSTOM_VARCHAR_FIELD1, CUSTOM_TIMESTAMP_FIELD1);
    }
	}
	
	@Override
  public String getColumnLabel(String column) {
    return Ivy.cms().co("/DefaultColumns/caseList/" + column);
  }

	@Override
	public void initFilterContainer() {
		filterContainer = new CustomizedCaseFilterContainer();
	}
}
