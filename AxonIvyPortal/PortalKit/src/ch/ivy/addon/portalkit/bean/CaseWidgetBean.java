package ch.ivy.addon.portalkit.bean;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import ch.ivy.addon.portalkit.bo.RemoteCase;
import ch.ivy.addon.portalkit.bo.RemoteSideStep;
import ch.ivy.addon.portalkit.casefilter.CaseFilterData;
import ch.ivy.addon.portalkit.enums.AdditionalProperty;
import ch.ivy.addon.portalkit.enums.PortalLibrary;
import ch.ivy.addon.portalkit.service.CaseFilterService;
import ch.ivy.addon.portalkit.service.IvyAdapterService;
import ch.ivy.addon.portalkit.support.UrlDetector;
import ch.ivy.addon.portalkit.util.CaseUtils;
import ch.ivy.addon.portalkit.util.NumberUtils;
import ch.ivy.addon.portalkit.util.UrlValidator;
import ch.ivyteam.ivy.model.value.WebLink;

@ManagedBean
@ViewScoped
public class CaseWidgetBean implements Serializable {

  private static final String START_PROCESSES_SHOW_ADDITIONAL_CASE_DETAILS_PAGE = "Start Processes/CaseWidget/showAdditionalCaseDetails.ivp";
  private static final String SUBPROCESS_SIGNATURE_LOAD_CASE_ADDITIONAL_PROPERTIES = "loadCaseAdditionalProperties(ch.ivy.addon.portalkit.bo.RemoteCase)";
  private static final String SUBPROCESS_PARAM_REMOTE_CASE = "remoteCase";
  private static final String HIDE = "HIDE";
  
  private static final long serialVersionUID = 1L;

  private Long expandedCaseId;
  private RemoteCase selectedCase;
  private Map<Long, RemoteCase> caseCache;

  public CaseWidgetBean() {
    expandedCaseId = -1L;
  }

  public Long getExpandedCaseId() {
    return expandedCaseId;
  }

  public void setExpandedCaseId(Long expandedCaseId, boolean alreadyExpanded) {
    if (alreadyExpanded) {
      this.expandedCaseId = 0L;
    } else {
      this.expandedCaseId = expandedCaseId;
    }
  }

  public RemoteCase getSelectedCase() {
    return selectedCase;
  }

  public void setSelectedCase(RemoteCase selectedCase) {
    this.selectedCase = selectedCase;
  }

  public boolean isDeleteFilterEnabledFor(CaseFilterData filterData) {
    CaseFilterService filterService = new CaseFilterService();
    return filterService.isDeleteFilterEnabledFor(filterData);
  }

  public String getAdditionalCaseDetailsPageUri(RemoteCase remoteCase) {
    String additionalCaseDetailsPageUri = getAdditionalCaseDetailsPageUriFromAdditionalProperty(remoteCase);
    if (StringUtils.isEmpty(additionalCaseDetailsPageUri)) {
      additionalCaseDetailsPageUri = CaseUtils.getProcessStartUriWithCaseParameters(remoteCase, START_PROCESSES_SHOW_ADDITIONAL_CASE_DETAILS_PAGE);
    }
    try {
      String host = (new UrlDetector()).getHost(remoteCase.getServerUrl(), remoteCase.getServer());
      WebLink webLink = UrlValidator.isValidUrl(additionalCaseDetailsPageUri) ? new WebLink(additionalCaseDetailsPageUri)
                                                                              : new WebLink(host + additionalCaseDetailsPageUri);
      return webLink.getAbsoluteEncoded();
    } catch (MalformedURLException e) {
      return additionalCaseDetailsPageUri;
    }
  }

  private String getAdditionalCaseDetailsPageUriFromAdditionalProperty(RemoteCase remoteCase) {
    Map<String, Object> params = new HashMap<>();
    params.put(SUBPROCESS_PARAM_REMOTE_CASE, remoteCase);
    Map<String, Object> response = IvyAdapterService.startSubProcess(SUBPROCESS_SIGNATURE_LOAD_CASE_ADDITIONAL_PROPERTIES, params,
            Arrays.asList(PortalLibrary.PORTAL_TEMPLATE.getValue()));
    RemoteCase responseRemoteCase = (RemoteCase) response.get(SUBPROCESS_PARAM_REMOTE_CASE);
    return responseRemoteCase.getAdditionalProperty(AdditionalProperty.CUSTOMIZATION_ADDITIONAL_CASE_DETAILS_PAGE.toString());
  }
  
  public boolean isNaN(Number number){
    return NumberUtils.isNaN(number);
  }

  public RemoteCase findRemoteCaseByCaseId(long caseId) {
    return CaseUtils.findRemoteCaseById(caseId);
  }

  public boolean isHiddenCase(RemoteCase remoteCase) {
    return remoteCase.getAdditionalProperty(HIDE) != null;
  }
  
  public void navigateToSideStep(RemoteSideStep sideStep) throws IOException {
    String url = sideStep.getStartLink().getAbsoluteEncoded();
    FacesContext.getCurrentInstance().getExternalContext().redirect(url);
  }
  
  public RemoteCase findRemoteCaseWithCaching(long caseId) {
    if (caseCache == null) {
      caseCache = new HashMap<>();
    }
    if (!caseCache.containsKey(caseId)) {
      RemoteCase iCase = CaseUtils.findRemoteCaseById(caseId);
      caseCache.put(caseId, iCase);
    }
    return caseCache.get(caseId);
    
  }
}
