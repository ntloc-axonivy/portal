package com.axonivy.portal.validator.filter;

import java.util.ArrayList;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.collections4.CollectionUtils;

import com.axonivy.portal.dto.dashboard.filter.DashboardFilter;
import com.axonivy.portal.util.filter.field.FilterFieldFactory;

import ch.ivyteam.ivy.environment.Ivy;

@FacesValidator(value = "dashboardTextFilterValidator")
public class DashboardTextFilterValidator implements Validator {

  private static final String MESSAGE_PREFIX_PATTERN = "%s(%d)";

  @SuppressWarnings("unchecked")
  public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
  
    DashboardFilter filter = (DashboardFilter) component.getAttributes().get("filter");
    Integer filterIndex = Optional.ofNullable((Integer)component.getAttributes().get("filterIndex")).orElse(0);
    String messageComponentId = Optional.ofNullable((String)component.getAttributes().get("messageId")).orElse(null);
    validateDefaultOperator((ArrayList<String>)value, filter, filterIndex, component, messageComponentId);
  }

  private void validateDefaultOperator(ArrayList<String> value, DashboardFilter filter, int filterIndex, UIComponent component, String messageComponentId) {
    if (CollectionUtils.isEmpty(value)) {
      FacesContext.getCurrentInstance().addMessage(
          messageComponentId,
          new FacesMessage(FacesMessage.SEVERITY_ERROR,
              getRequiredMessage(filter.getField(), filterIndex), null));
      invalidate(component);
    }
  }

  private void invalidate(UIComponent component) {
    FacesContext.getCurrentInstance().validationFailed();
    UIInput input = (UIInput) component;
    input.setValid(false);
  }

  private String getMessagePrefix(String field, int index) {
    return String.format(MESSAGE_PREFIX_PATTERN,
        FilterFieldFactory.findBy(Optional.ofNullable(field).orElse("")).getLabel(), index + 1);
  }

  public String getRequiredMessage(String field, int index) {
    return String.join(": ", getMessagePrefix(field, index), Ivy.cms().co("/ch.ivy.addon.portalkit.ui.jsf/common/requiredFieldMessage"));
  }
}
