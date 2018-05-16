package ch.ivy.addon.portalkit.datacollecting.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ch.ivy.addon.portalkit.bo.RemoteLibrary;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.process.call.SubProcessCall;
import ch.ivyteam.ivy.server.ServerFactory;

public class MultiServerLibraryService extends AbstractLibraryService {

  private static final String LIBRARY_SERVICE_CALLABLE = "MultiPortal/LibraryService";

  @Override
  public Map<String, List<RemoteLibrary>> collectLibraries() {
    try {
      @SuppressWarnings("unchecked")
      List<RemoteLibrary> libraries =
          ServerFactory
              .getServer()
              .getSecurityManager()
              .executeAsSystem(
                  () -> SubProcessCall.withPath(LIBRARY_SERVICE_CALLABLE).withStartName("getLibraries")
                      .withParam("username", Ivy.session().getSessionUserName()).call().get("libraries", List.class));

      return libraries.stream().collect(Collectors.groupingBy(RemoteLibrary::getApplication));
    } catch (Exception e) {
      Ivy.log().error("Can't get project versions", e);
      return new HashMap<>();
    }
  }
}
