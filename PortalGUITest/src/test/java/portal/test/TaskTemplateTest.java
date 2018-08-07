package portal.test;

import org.junit.Before;
import org.junit.Test;

import portal.common.BaseTest;
import portal.common.TestAccount;
import portal.page.HomePage;
import portal.page.LoginPage;
import portal.page.TaskTemplatePage;
import portal.page.TaskWidgetPage;

public class TaskTemplateTest extends BaseTest {

  @Before
  public void setup() {
    super.setup();
    String prepareTaskForTestUrl = "internalSupport/14B2FC03D2E87141/CreateTestTasks.ivp";
    navigateToUrl(prepareTaskForTestUrl);
    redirectToRelativeLink(HomePage.PORTAL_HOME_PAGE_URL);

    LoginPage loginPage = new LoginPage(TestAccount.DEMO_USER);
    loginPage.login();
  }

  @Test
  public void testCaseDetailsTabDisplayed() {
    TaskTemplatePage taskTemplatePage = startATask();
    assertTrue("Case details is not displayed", taskTemplatePage.containsCaseDetails());
  }

  @Test
  public void testAddingANote() {
    TaskTemplatePage taskTemplatePage = startATask();
    assertEquals(1, taskTemplatePage.countHistoryItems());
    taskTemplatePage.addNewNote("Sample note message");
    assertEquals(1, taskTemplatePage.countNoteItems());
    assertEquals(2, taskTemplatePage.countHistoryItems());
  }

  @Test
  public void testOpeningFinishedTaskInHistoryArea() {
    TaskTemplatePage taskTemplatePage = startATask();
    TaskWidgetPage taskWidget = taskTemplatePage.openFinishedTaskInHistoryArea();
    assertTrue(taskWidget.countTasks() > 0);
  }

  @Test
  public void testOpeningRelatedTask() {
    TaskTemplatePage taskTemplatePage = startATask();
    assertTrue(taskTemplatePage.countRelatedTasks() > 0);
    TaskWidgetPage taskWidget = taskTemplatePage.openFirstRelatedTaskInHistoryArea();
    assertTrue(taskWidget.countTasks() > 0);
  }

  @Test
  public void testOpeningDocumentUploading() {
    TaskTemplatePage taskTemplatePage = startATask();
    taskTemplatePage.openDocumentUploadingDialog();
    assertTrue(taskTemplatePage.isDocumentUploadingDialogDisplayed());
  }

  private TaskTemplatePage startATask() {
    TaskWidgetPage taskWidgetPage = new TaskWidgetPage();
    TaskTemplatePage taskTemplatePage = taskWidgetPage.startTask(0);
    taskTemplatePage.openStatusTab();
    return taskTemplatePage;
  }
}
