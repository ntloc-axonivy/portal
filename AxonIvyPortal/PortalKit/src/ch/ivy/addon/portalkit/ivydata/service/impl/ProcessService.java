package ch.ivy.addon.portalkit.ivydata.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import ch.ivy.addon.portalkit.ivydata.dto.IvyProcessResultDTO;
import ch.ivy.addon.portalkit.ivydata.exception.PortalIvyDataErrorType;
import ch.ivy.addon.portalkit.ivydata.exception.PortalIvyDataException;
import ch.ivy.addon.portalkit.ivydata.searchcriteria.ProcessSearchCriteria;
import ch.ivy.addon.portalkit.ivydata.service.IProcessService;
import ch.ivy.addon.portalkit.ivydata.utils.ServiceUtilities;
import ch.ivy.addon.portalkit.util.IvyExecutor;
import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.security.ISecurityContext;
import ch.ivyteam.ivy.workflow.IProcessStart;
import ch.ivyteam.ivy.workflow.IWorkflowSession;

public class ProcessService implements IProcessService {

  private static final String PORTAL_START_REQUEST_PATH = "/DefaultApplicationHomePage.ivp";

  private ProcessService() {}

  public static ProcessService newInstance() {
    return new ProcessService();
  }

  @Override
  public IvyProcessResultDTO findProcesses(ProcessSearchCriteria criteria) {
    return IvyExecutor.executeAsSystem(() -> { 
      IvyProcessResultDTO result = new IvyProcessResultDTO();
      if (criteria == null || CollectionUtils.isEmpty(criteria.getApps())) {
        return result;
      }

      List<PortalIvyDataException> errors = new ArrayList<>();
      List<IProcessStart> processes = new ArrayList<>();
      criteria.getApps().stream().forEach(app -> {
        IWorkflowSession session = null;
        IApplication application = null;
        try {
          application = ServiceUtilities.findApp(app);
          session = ServiceUtilities.findUserWorkflowSession(criteria.getUsername(), application);
          processes.addAll(findStartablesWithoutPortalHome(session));
        } catch (PortalIvyDataException e) {
          errors.add(e);
        } catch (Exception ex) {
          Ivy.log().error("Error in getting processes of user {0} within app {1}", ex, criteria.getUsername(), app);
          errors.add(new PortalIvyDataException(app, PortalIvyDataErrorType.FAIL_TO_LOAD_PROCESS.toString()));
        } finally {
          if (session != null && application != null && !Objects.equals(Ivy.wf().getApplication(), application)) {
            ISecurityContext securityContext = application.getSecurityContext();
            securityContext.destroySession(session.getIdentifier());
          }
        }
      });
      result.setErrors(errors);
      result.setProcesses(processes);
      return result;
    });
  }

  private List<IProcessStart> findStartablesWithoutPortalHome(IWorkflowSession session) {
    return session.getStartableProcessStarts().stream()
        .filter(process -> !process.getLink().getRelativeEncoded().endsWith(PORTAL_START_REQUEST_PATH))
        .collect(Collectors.toList());
  }

}
