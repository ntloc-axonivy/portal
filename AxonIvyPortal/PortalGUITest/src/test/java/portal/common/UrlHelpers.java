package portal.common;

import java.util.Optional;

import org.apache.commons.lang.WordUtils;

public class UrlHelpers {

  public static String generateAbsoluteProcessStartLink(String relativeProcessStartLink) {
    if (!SystemProperties.isInServerMode() || System.getProperty("engineUrl") != null) {
      relativeProcessStartLink = WordUtils.capitalize(relativeProcessStartLink);
    }
    return getEngineUrl() + "/pro/" + getApplicationName() + "/" + relativeProcessStartLink;
  }

  private static String getApplicationName() {
    String applicationName = System.getProperty("engineApplicationName");
    return Optional.ofNullable(applicationName).orElse(PropertyLoader.getApplicationName());
  }

  private static String getEngineUrl() {
    String vmArgUrl = System.getProperty("engineUrl");
    return Optional.ofNullable(vmArgUrl).orElse(
        "http://" + PropertyLoader.getServerAddress() + ":" + PropertyLoader.getIvyEnginePort() + "/"
            + PropertyLoader.getIvyContextPath());
  }
}
