package ch.ivy.addon.portalkit.ivydata.dto;

import java.util.ArrayList;
import java.util.List;

import ch.ivy.addon.portalkit.ivydata.exception.PortalIvyDataException;

public class AbstractResultDTO {

  private List<PortalIvyDataException> errors = new ArrayList<>();
  
  public List<PortalIvyDataException> getErrors() {
    return errors;
  }

  public void setErrors(List<PortalIvyDataException> errors) {
    this.errors = errors;
  }
}
