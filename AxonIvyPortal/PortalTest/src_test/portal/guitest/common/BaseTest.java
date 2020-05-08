package portal.guitest.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.jayway.awaitility.Awaitility;
import com.jayway.awaitility.Duration;

import ch.ivy.addon.portalkit.enums.PortalPermission;
import portal.guitest.page.HomePage;
import vn.wawa.guitest.base.client.Browser;
import vn.wawa.guitest.base.enums.BrowserType;

/**
 * A base test that other tests extend it. It will test on browser IE by default. It provides feature to take screenshot
 * of failed tests and utility methods.
 *
 */
public class BaseTest {
  private Browser browser;

  private String designerLogoutUrl = "http://localhost:8081/wf/logout.jsp";
  private final static String LOGIN_URL_PATTERN = "portalKitTestHelper/1636734E13CEC872/login.ivp?username=%s&password=%s";
  private BrowserType browserType;

  public BaseTest() {
    String vmArgPath = System.getProperty("browserType");
    if (vmArgPath != null) {
      browserType = BrowserType.valueOf(vmArgPath);
    } else {
      browserType = BrowserType.valueOf(PropertyLoader.getBrowserType());
    }
  }
  
  public Browser getBrowser() {
    return browser;
  }
  
  public void setBrowser(Browser browser) {
    this.browser = browser;
  }
  
  protected String createTestingTasksUrl = "portal-developer-examples/162511D2577DBA88/CategoriedLeaveRequest.ivp";
  protected String businessCaseUrl = "internalSupport/15B1EA24CCF377E8/updateCheckInTime.ivp";
  protected String hideCaseUrl = "portal-developer-examples/16583F0F73864543/createHiddenTechnicalCase.ivp";
  protected String createTestingCaseMapUrl = "internalSupport/764871e4-cf70-401f-83fb-9e99fa897fc4.icm";
  protected String createTestingCaseUrlForCustomizationAdditionalCaseDetails = "portal-developer-examples/1624D1F5CBEA5332/createInvestmentRequest.ivp";
  protected String createTestingCaseUrlForDefaultAdditionalCaseDetails = "internalSupport/14B2FC03D2E87141/DefaultAdditionalCaseDetails.ivp";
  protected String createTestingCaseContainOneTask = "internalSupport/14B2FC03D2E87141/CreateSupportTicket.ivp";
  protected String createUnassignedTaskUrl = "internalSupport/14B2FC03D2E87141/createUnassignedTask.ivp";

  @Rule
  public ScreenshotFailedTestRule screenshotTestRule = new ScreenshotFailedTestRule();
  
  @Rule
  public TestWatcher watchman= new TestWatcher() {

    @Override
    protected void starting(Description description) {
      super.starting(description);
      System.out.println("Starting test: " + description.getMethodName());
    }
    
  };
  
  @Before
  public void setup() {
    browser = Browser.getBrowser();
    launchBrowserAndGotoRelativeLink("portalKitTestHelper/1511A66AF619A768/cleanData.ivp");
  }

  public void launchBrowserAndGotoRelativeLink(String relativeProcessStartLink) {
    try {
      browser.launch(browserType, UrlHelpers.generateAbsoluteProcessStartLink(relativeProcessStartLink), getDriverPath());
    } catch (Exception e) {
      throw new PortalGUITestException(e);
    }
  }

  public void redirectToRelativeLink(String relativeProcessStartUrl) {
    try {
      browser.goHome(UrlHelpers.generateAbsoluteProcessStartLink(relativeProcessStartUrl));
    } catch (Exception e) {
      throw new PortalGUITestException(e);
    }
  }

  public void launchBrowserAndLogoutInDesigner() {
    try {
      browser.launch(browserType, designerLogoutUrl, getDriverPath());
    } catch (Exception e) {
      throw new PortalGUITestException(e);
    }
  }

  protected void logoutDesigner() {
    try {
      browser.goHome(designerLogoutUrl);
      redirectToRelativeLink(HomePage.PORTAL_HOME_PAGE_URL);
    } catch (Exception e) {
      throw new PortalGUITestException(e);
    }
  }

  public void createTestingTasks() {
    redirectToRelativeLink(createTestingTasksUrl);
  }
  
  public void grantTaskReadAllPermissionsToCurrentUser() {
    String grantAllPermissionsForAdminUserURL = "portalKitTestHelper/14DE09882B540AD5/grantTaskReadAllPermissionsToCurrentUser.ivp";
    redirectToRelativeLink(grantAllPermissionsForAdminUserURL);
  }
  
  public void grantTaskReadOwnCaseTaskPermissionsToCurrentUser() {
    String grantAllPermissionsForAdminUserURL = "portalKitTestHelper/14DE09882B540AD5/grantTaskReadOwnCaseTaskPermissionsToCurrentUser.ivp";
    redirectToRelativeLink(grantAllPermissionsForAdminUserURL);
  }

  public void denyReadAllPermissionFromCurrentUser() {
    String grantAllPermissionsForAdminUserURL = "portalKitTestHelper/14DE09882B540AD5/denyReadAllPermissionFromCurrentUser.ivp";
    redirectToRelativeLink(grantAllPermissionsForAdminUserURL);
  }
  
  public void denyDocumentOfInvolvedCaseWritePemissionFromCurrentUser() {
    String denyDocumentOfInvolvedCaseWritePemissionURL = "portalKitTestHelper/14DE09882B540AD5/denyDocumentOfInvolvedCaseWritePemission.ivp";
    redirectToRelativeLink(denyDocumentOfInvolvedCaseWritePemissionURL);
  }
  
  public void grantDocumentOfInvolvedCaseWritePemissionToCurrentUser() {
    String grantDocumentOfInvolvedCaseWritePemissionURL = "portalKitTestHelper/14DE09882B540AD5/grantDocumentOfInvolvedCaseWritePemission.ivp";
    redirectToRelativeLink(grantDocumentOfInvolvedCaseWritePemissionURL);
  }
  
  public void grantSpecificPortalPermission(PortalPermission portalPermission) {
    String grantSpecificPortalPermissionLink = "portalKitTestHelper/14DE09882B540AD5/grantSpecificPortalPermission.ivp?portalPermission=%s";
    redirectToRelativeLink(String.format(grantSpecificPortalPermissionLink, portalPermission.getValue()));
  }

  public void denySpecificPortalPermission(PortalPermission portalPermission) {
    String denySpecificPortalPermissionLink = "portalKitTestHelper/14DE09882B540AD5/denySpecificPortalPermission.ivp?portalPermission=%s";
    redirectToRelativeLink(String.format(denySpecificPortalPermissionLink, portalPermission.getValue()));
  }

  public void cleanUpGlobalVariables(){
    String cleanUpURL = "portalKitTestHelper/1511A66AF619A768/cleanupGlobalVars.ivp";
    redirectToRelativeLink(cleanUpURL);
  }
  
  public void resetLanguageOfCurrentUser(){
    redirectToRelativeLink("portalKitTestHelper/1511A66AF619A768/resetLanguageOfCurrentUser.ivp");
  }
  
  public void refreshPage(){
    browser.getDriver().navigate().refresh();
  }
  
  protected void login(TestAccount testAccount) {
    String username;
    String password;
    try {
      username = URLEncoder.encode(testAccount.getUsername(), "UTF-8");
      password = URLEncoder.encode(testAccount.getPassword(), "UTF-8");

      AtomicBoolean isLoginSuccess = new AtomicBoolean(false);
      Awaitility.await().atMost(new Duration(30, TimeUnit.SECONDS)).until(() -> {
        try {
          redirectToRelativeLink(String.format(LOGIN_URL_PATTERN, username, password));
          new HomePage() {
            @Override
            protected long getTimeOutForLocator() {
              return 7L;
            }
          }.isDisplayed();
          isLoginSuccess.set(true);
        } catch (Exception e) {
          System.out.println("*****Login unsuccessfully. Try again if not timeout.");
        }
        return isLoginSuccess.get();

      });

    } catch (UnsupportedEncodingException e) {
      throw new PortalGUITestException(e);
    }
  }
  
  public static void killBrowsers() {
    try {
      System.out.println("Kill all open browsers");
      Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
      Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
      Sleeper.sleep(5000);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private String getDriverPath() {
    return browserType.getConfiguration().getDriverPath();
  }

}
