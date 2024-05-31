package ch.ivy.addon.portalkit.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.axonivy.portal.components.publicapi.IWebStartableAPI;

import ch.ivy.addon.portal.generic.navigation.PortalNavigator;
import ch.ivy.addon.portalkit.constant.CustomFields;
import ch.ivy.addon.portalkit.enums.AdditionalProperty;
import ch.ivy.addon.portalkit.enums.GlobalVariable;
import ch.ivy.addon.portalkit.enums.PortalPermission;
import ch.ivy.addon.portalkit.service.ExpressProcessService;
import ch.ivy.addon.portalkit.service.GlobalSettingService;
import ch.ivy.addon.portalkit.util.PermissionUtils;
import ch.ivy.addon.portalkit.util.PortalProcessViewerUtils;
import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.security.IPermission;
import ch.ivyteam.ivy.workflow.ICase;
import ch.ivyteam.ivy.workflow.start.IWebStartable;

@ManagedBean
@RequestScoped
public class CaseActionBean implements Serializable {

  private static final long serialVersionUID = 7468665222036995531L;
  private static final String START_PROCESSES_SHOW_ADDITIONAL_CASE_DETAILS_PAGE = "Start Processes/PortalStart/showAdditionalCaseDetails.ivp";
  private boolean isShowCaseDetails;

  @PostConstruct
  public void initCaseActions() {
    isShowCaseDetails = PermissionUtils.hasPortalPermission(PortalPermission.SHOW_CASE_DETAILS);
  }

  public String getAdditionalCaseDetailsPageUri(ICase iCase) {
    String additionalCaseDetailsPageUri = StringUtils.EMPTY;
    if (isExpressCase(iCase)) {
      additionalCaseDetailsPageUri =
          ExpressProcessService.getInstance().findExpressBusinessViewStartLink() + "?uuid=" + iCase.uuid();
    } else {
      additionalCaseDetailsPageUri = getBusinessDetailURLFromCustomField(iCase);
    }
    return additionalCaseDetailsPageUri;
  }

  private String getBusinessDetailURLFromCustomField(ICase iCase) {
    String additionalCaseDetailsPageUri = iCase.customFields()
        .stringField(com.axonivy.portal.components.constant.CustomFields.BUSINESS_DETAILS).getOrNull();
    if (StringUtils.isEmpty(additionalCaseDetailsPageUri)) {
      additionalCaseDetailsPageUri = iCase.customFields()
          .textField(AdditionalProperty.CUSTOMIZATION_ADDITIONAL_CASE_DETAILS_PAGE.toString()).getOrNull();
      if (StringUtils.isNotEmpty(additionalCaseDetailsPageUri)) {
        Boolean isEmbedInFrame = true;
        if (!additionalCaseDetailsPageUri.contains("embedInFrame")) {
          isEmbedInFrame = false;
        }
        String processUrl = removeQueryParameters(additionalCaseDetailsPageUri);
        IWebStartable iWebStartable = IWebStartableAPI.findIWebStartableByProcessRelativeLink(processUrl);
        iCase.customFields().stringField(com.axonivy.portal.components.constant.CustomFields.BUSINESS_DETAILS)
            .set(iWebStartable.getId());
        iCase.customFields().numberField(CustomFields.EMBED_IN_FRAME).set(isEmbedInFrame ? 1 : 0);
        Ivy.log().error("Case 3 Finall: " + iWebStartable.getLink().getRelative() + "?uuid=" + iCase.uuid()
            + (isEmbedInFrame ? "&embedInFrame" : ""));
        return iWebStartable.getLink().getRelative() + "?uuid=" + iCase.uuid()
            + (isEmbedInFrame ? "&embedInFrame" : "");
      }
    } else if (additionalCaseDetailsPageUri.startsWith(IApplication.current().getName())) {
      Ivy.log().error("case 1");
      Boolean isEmbedInFrame =
          (iCase.customFields().numberField(com.axonivy.portal.components.constant.CustomFields.EMBED_IN_FRAME).get()
              .orElse(1).intValue() == 1 ? true : false);
      return additionalCaseDetailsPageUri + "?uuid=" + iCase.uuid() + (isEmbedInFrame ? "&embedInFrame" : "");
    } else if (detectExternalLink(additionalCaseDetailsPageUri)) {
      Ivy.log().error("case 2");
      return additionalCaseDetailsPageUri;
    } else {
      Ivy.log().error("case 3");
      Ivy.log().error("URL____" + additionalCaseDetailsPageUri);
      Boolean isEmbedInFrame = true;
      if (!additionalCaseDetailsPageUri.contains("embedInFrame")) {
        isEmbedInFrame = false;
      }
      String processUrl = removeQueryParameters(additionalCaseDetailsPageUri);
      IWebStartable iWebStartable = IWebStartableAPI.findIWebStartableByProcessRelativeLink(processUrl);
      iCase.customFields().stringField(com.axonivy.portal.components.constant.CustomFields.BUSINESS_DETAILS)
          .set(iWebStartable.getId());
      iCase.customFields().numberField(CustomFields.EMBED_IN_FRAME).set(isEmbedInFrame ? 1 : 0);
      Ivy.log().error("Case 3 Finall: " + iWebStartable.getLink().getRelative() + "?uuid=" + iCase.uuid()
          + (isEmbedInFrame ? "&embedInFrame" : ""));
      return iWebStartable.getLink().getRelative() + "?uuid=" + iCase.uuid() + (isEmbedInFrame ? "&embedInFrame" : "");
    }
    if (StringUtils.isEmpty(additionalCaseDetailsPageUri)) {
      Map<String, String> params = new HashMap<>();
      params.put("uuid", String.valueOf(iCase.uuid()));
      additionalCaseDetailsPageUri = PortalNavigator.buildUrlByKeyword("showAdditionalCaseDetails",
          START_PROCESSES_SHOW_ADDITIONAL_CASE_DETAILS_PAGE, params);
    }
    return additionalCaseDetailsPageUri;
  }

  public String getProcessViewerPageUri(ICase selectedCase) {
    return PortalProcessViewerUtils.getStartProcessViewerPageUri(selectedCase);
  }

  private boolean isExpressCase(ICase iCase) {
    return BooleanUtils.toBoolean(iCase.customFields().stringField(CustomFields.IS_EXPRESS_PROCESS).getOrNull());
  }

  public boolean showProcessOverviewLink(ICase iCase) {
    return GlobalSettingService.getInstance().findGlobalSettingValueAsBoolean(GlobalVariable.SHOW_PROCESS_INFORMATION)
        && !isExpressCase(iCase) && iCase.isBusinessCase();
  }

  public String getProcessOverviewPageUri(ICase iCase) {
    if (Objects.isNull(iCase) || Objects.isNull(iCase.getApplication()) || Objects.isNull(iCase.getProcessStart())) {
      return "#";
    }
    return PortalNavigator.buildProcessInfoUrl(iCase.getApplication().getName().concat("/")
        .concat(iCase.getProcessStart().getFullUserFriendlyRequestPath()));
  }

  public boolean canChangeName(ICase iCase) {
    return hasPermission(iCase, IPermission.CASE_WRITE_NAME);
  }

  public boolean canChangeDescription(ICase iCase) {
    return hasPermission(iCase, IPermission.CASE_WRITE_DESCRIPTION);
  }

  public boolean canDestroy(ICase iCase) {
    return hasPermission(iCase, IPermission.CASE_DESTROY);
  }

  private boolean hasPermission(ICase iCase, IPermission permission) {
    if (iCase == null || permission == null) {
      return false;
    }
    return PermissionUtils.hasPermission(permission);
  }

  public boolean isShowCaseDetails() {
    return isShowCaseDetails;
  }

  public void setShowCaseDetails(boolean isShowCaseDetails) {
    this.isShowCaseDetails = isShowCaseDetails;
  }

  public boolean showProcessViewer(ICase caze) {
    return PortalProcessViewerUtils.isShowProcessViewer(caze);
  }

  private static boolean detectExternalLink(String path) {
    return StringUtils.startsWithIgnoreCase(path, "http") || StringUtils.startsWithIgnoreCase(path, "https");
  }

  private String removeQueryParameters(String url) {
    int queryIndex = url.indexOf("?");
    if (queryIndex != -1) {
      return url.substring(0, queryIndex);
    } else {
      return url;
    }
  }


}
