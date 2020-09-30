package portal.guitest.page;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class UserProfilePage extends TemplatePage {
  
  private static final String MAIL_NOTI_ON_TASK_ASSIGNMENT_SELECTOR = "div[id$=':mail-notification-on-task-assign']";
  private static final String FURTHER_EMAIL_FROM_APP_SELECTOR = "div[id$=':further-mails-from-application']";
  private static final String SELECTED_DAY_XPATH="//*[@id='my-profile-form:daily-summary']/div/div/div/div[2]";
  private static final String SHOW_TUTORIAL_XPATH = "//*[@id='my-profile-form:general-show-tutorial']/div[2]";
  
  @Override
  protected String getLoadedLocator() {
    return "id('my-profile-form:save-settings')";
  }
  
  public void selectLanguage(int newLanguage) {
    waitForElementDisplayed(By.cssSelector("div[id$='selection'] div.ui-selectonemenu-trigger"), true);
    clickByCssSelector("div[id$='selection'] div.ui-selectonemenu-trigger");
    clickByCssSelector("li[id$='selection_" + newLanguage + "']");
  }

  public UserProfilePage save() {
    WebElement save = findElementByCssSelector("button[id$='save-settings']");
    click(save);
    waitAjaxIndicatorDisappear();
    waitForPageLoaded();
    return new UserProfilePage();
  }
  
  public String getLanguageSettingTitle() {
    return findElementByCssSelector("h2[id$='language-setting-title']").getText();
  }
  
  public void switchOnEmailOnTaskAssignmentSetting() {
    switchOnSetting(MAIL_NOTI_ON_TASK_ASSIGNMENT_SELECTOR);
  }
  
  public void switchOnFurtherEmailFromAppSetting() {
    switchOnSetting(FURTHER_EMAIL_FROM_APP_SELECTOR);
  }
  
  public void switchOffEmailOnTaskAssignmentSetting() {
    switchOffSetting(MAIL_NOTI_ON_TASK_ASSIGNMENT_SELECTOR);
  }
  
  public void switchOffFurtherEmailFromAppSetting() {
    switchOffSetting(FURTHER_EMAIL_FROM_APP_SELECTOR);
  }

  private void switchOnSetting(String cssSelector) {
    WebElement inputSwitch = findElementByCssSelector(cssSelector);
    if (!inputSwitch.getAttribute("class").contains("ui-inputswitch-checked")) {
      click(inputSwitch);
    }
  }
  
  private void switchOffSetting(String cssSelector) {
    WebElement inputSwitch = findElementByCssSelector(cssSelector);
    if (inputSwitch.getAttribute("class").contains("ui-inputswitch-checked")) {
      click(inputSwitch);
    }
  }

  public void selectDaysForDailySummary(List<Integer> indices) {
    List<WebElement> selectDays = findListElementsByXpath(SELECTED_DAY_XPATH);
    for(int index : indices) {
      WebElement selectedDayCheckbox = selectDays.get(index);
      if (!selectedDayCheckbox.getAttribute("class").contains("ui-state-active")) {
        click(selectedDayCheckbox);
      }
    }
  }

  public int getSelectedDaySummary() {
    return findListElementsByXpath(SELECTED_DAY_XPATH)
        .stream()
        .filter(checkbox -> checkbox.getAttribute("class").contains("ui-state-active"))
        .collect(Collectors.toList())
        .size();
  }
  
  public boolean isSettingSwitchedOn(String cssSelector) {
    WebElement inputSwitch = findElementByCssSelector(cssSelector);
    return inputSwitch != null ? inputSwitch.getAttribute("class").contains("ui-inputswitch-checked") : false;
  }
  
  public boolean isEmailOnTaskAssignmentSettingSwitchedOn() {
    return isSettingSwitchedOn(MAIL_NOTI_ON_TASK_ASSIGNMENT_SELECTOR);
  }
  
  public boolean isFurtherEmailFromAppSettingSwitchedOn() {
    return isSettingSwitchedOn(FURTHER_EMAIL_FROM_APP_SELECTOR);
  }
  
  public WebElement getUserSettingCard() {
    return findElementById("my-profile-container");
  }

  public boolean isDisableShowTutorialCheckbox() {
    WebElement checkbox = findElementByCssSelector("[id$=':general-show-tutorial']");
    return checkbox.getAttribute("class").contains("ui-state-disabled");
  }

  public void checkShowTutorial() {
    WebElement checkbox = findElementByXpath(SHOW_TUTORIAL_XPATH);
    if (!checkbox.getAttribute("class").contains("ui-state-active")) {
      click(checkbox.findElement(By.cssSelector("span[class='ui-chkbox-label']")));
      waitForElementDisplayed(By.xpath(SHOW_TUTORIAL_XPATH + "/span[@class='ui-chkbox-icon ui-icon ui-c ui-icon-check']"), true);
    }
  }

  public void changeHomePageToCase() {
    String homepageLabel = "my-profile-form:homepage_label";
    click(By.id(homepageLabel));
    String caseItemId = "my-profile-form:homepage_3";
    waitForElementDisplayed(By.id(caseItemId), true);
    click(By.id(caseItemId));
    waitUntilTextToBePresentInElement(findElementById(homepageLabel), "Cases", getTimeOutForLocator());
  }
}
