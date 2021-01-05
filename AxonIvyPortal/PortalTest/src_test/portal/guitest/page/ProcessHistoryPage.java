package portal.guitest.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProcessHistoryPage extends TemplatePage {

  public int countCases() {
    WebElement caseList = findElementByClassName("ui-datascroller-list");
    return caseList.findElements(By.className("ui-datascroller-item")).size();
  }
  
  public boolean isEmptyMessageDisplay() {
    return isElementDisplayed(By.className("process-history-empty-message"));
  }
  
  @SuppressWarnings("deprecation")
  public int openDialogAndCountCases() {
    click(findElementById("process-history-dialog-button"));
    waitAjaxIndicatorDisappear();
    WebElement caseList = findElementByClassName("ui-datascroller-list");
    return caseList.findElements(By.className("ui-datascroller-item")).size();
  }
  
  public WebElement getProcessHistoryDialog() {
    click(findElementById("process-history-dialog-button"));
    waitForElementDisplayed(By.id("process-history-dialog"), true);
    return findElementById("process-history-dialog");
  }
}
