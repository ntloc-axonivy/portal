package ch.ivy.addon.portal.generic.navigation;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.primefaces.PrimeFaces;

import ch.ivyteam.ivy.environment.Ivy;

/**
 * This class introduces the methods which navigate to specific pages and are only used inside Portal Frame
 */
public final class PortalNavigatorInFrame {
  
  /**
   * Navigates to PortalEndPage without finishing a task, e.g. clicking on Cancel button then back to previous page: task list or task details or global search
   */
  public static void navigateToPortalEndPage() {
    String statement = "parent.redirectToEndPageCommand([{name: 'taskId', value: " + Ivy.wfTask().getId() + "}]);";
    PrimeFaces.current().executeScript(statement);
  }

  public static void navigateToUrl(String url) throws UnsupportedEncodingException {
    String statement = "parent.redirectToUrlCommand([{name: 'url', value: '" + URLDecoder.decode(url, "UTF-8") + "'}])";
    PrimeFaces.current().executeScript(statement);
  }
}
