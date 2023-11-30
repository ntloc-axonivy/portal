package com.axonivy.portal.selenium.page;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.WebElement;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

public class TaskEditWidgetNewDashBoardPage extends TemplatePage {

  private String taskEditWidgetId;
  private static final String TASK_NAME = "Task name";
  private static final String STATE = "State";

  public TaskEditWidgetNewDashBoardPage() {
    this("div[id='new-widget-configuration-dialog']");
  }

  public TaskEditWidgetNewDashBoardPage(String taskWidgetId) {
    this.taskEditWidgetId = taskWidgetId;
  }

  @Override
  protected String getLoadedLocator() {
    return ".task-configuration__responsibles";
  }

  private SelenideElement widgetTitle() {
    return $(taskEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$("span[id$='widget-title-group']")
        .$("input[id$='widget-title']");
  }

  public void changeWidgetTitle(String name) {
    widgetTitle().clear();
    widgetTitle().sendKeys(name);
  }
  
  private int getIndexFiltertByName(String name) {
    ElementsCollection elementsTH =
        $(taskEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$("div[id$='user-filter']").$$("div");
    for (int i = 0; i < elementsTH.size(); i++) {
      if (elementsTH.get(i).getText().equalsIgnoreCase(name)) {
        return i;
      }
    }
    return 0;
  }
  
  private SelenideElement getAvailableFilterInput(String filterName) {
    int index = getIndexFiltertByName(filterName);
    return $(taskEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$("div[id$='user-filter']").$$("div").get(index + 1)
        .$("input");
  }
  
  public void filterTaskName(String taskName) {
    getAvailableFilterInput(TASK_NAME).sendKeys(taskName);
  }
  
  public void clickOnStateToShowDropdown() {
    int index = getIndexFiltertByName(STATE);
    $(taskEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$("div[id$='user-filter']").$$("div").get(index + 1)
        .click();
  }
  
  private SelenideElement getValueOfCheckBox(String value) {
    return $("div[style*='display: block;'] div.ui-selectcheckboxmenu-items-wrapper")
        .shouldBe(appear, DEFAULT_TIMEOUT).$$("li.ui-selectcheckboxmenu-item").filter(text(value)).first().$("div.ui-chkbox-box");
  }
  
  private SelenideElement getCloseCheckBox() {
    return $("div.ui-selectcheckboxmenu-panel[style*='display: block;']").shouldBe(appear, DEFAULT_TIMEOUT).$("a.ui-selectcheckboxmenu-close");
  }
  
  public void selectState(String state) {
    getValueOfCheckBox(state).shouldBe(getClickableCondition()).click();
    getCloseCheckBox().shouldBe(getClickableCondition()).click();
  }
  
  public void preview() {
    $(taskEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$("button[id$='preview-button']")
        .shouldBe(getClickableCondition()).click();
  }
  
  private ElementsCollection getColumnsOfTableWidget() {
    return $(taskEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$$("table tbody tr td");
  }
  
  private ElementsCollection getAllTasksOfTaskWidget() {
    return getColumnsOfTableWidget().filter(Condition.cssClass("dashboard-tasks__name"));
  }
  
  public ElementsCollection countAllTasks() {
    return getAllTasksOfTaskWidget();
  }
  
  
  public void nextPageTable() {
    $(taskEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$("div[id$='widget-preview']")
    .shouldBe(appear, DEFAULT_TIMEOUT).$("a.ui-paginator-next").shouldBe(getClickableCondition(), DEFAULT_TIMEOUT).click();
  }
  
  public void waitPageSelected(int pageNumber) {
    $(taskEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$("div[id$='widget-preview']")
    .shouldBe(appear, DEFAULT_TIMEOUT).$$("a.ui-paginator-page").get(pageNumber-1).shouldBe(Condition.attributeMatching("class", ".*ui-state-active.*"), DEFAULT_TIMEOUT);
  }
  
  public void save() {
    $(taskEditWidgetId).shouldBe(appear, DEFAULT_TIMEOUT).$("button[id$='widget-configuration-save-button']")
        .shouldBe(getClickableCondition()).click();
    $("button[id$='widget-configuration-save-button']").shouldBe(disappear, DEFAULT_TIMEOUT);
    $("[id$='task-component:loading']").shouldBe(disappear, DEFAULT_TIMEOUT);
    $(taskEditWidgetId).shouldBe(disappear, DEFAULT_TIMEOUT);
  }

  public void waitPreviewTableLoaded() {
    $(taskEditWidgetId).$("div[id$=':dashboard-tasks-container']").shouldBe(appear, DEFAULT_TIMEOUT);
  }

  public SelenideElement getAddLanguageButton() {
    SelenideElement addLanguageButton = $("button[id$='add-language-button']");
    addLanguageButton.shouldBe(Condition.appear, DEFAULT_TIMEOUT);
    addLanguageButton.shouldBe(getClickableCondition(), DEFAULT_TIMEOUT);
    waitUntilElementToBeClickable(addLanguageButton);
    return addLanguageButton;
  }

  public SelenideElement getMultipleLanguageDialog() {
    SelenideElement addLanguageButton = $("div[id$='multiple-languages-dialog']");
    addLanguageButton.shouldBe(Condition.appear, DEFAULT_TIMEOUT);
    return addLanguageButton;
  }

  public SelenideElement getTranslationOverlayPanel(int index) {
    SelenideElement translationOverlay = $(String.format("div[id$=':%s:overlay-panel-input']", index));
    waitUntilElementToBeClickable(translationOverlay);
    translationOverlay.shouldBe(Condition.appear, DEFAULT_TIMEOUT);

    return translationOverlay;
  }

  public WebElement getConfigurationFilter() {
    return $("[id='widget-configuration-form:new-widget-configuration-component:filter-container']").shouldBe(appear, DEFAULT_TIMEOUT);
  }

  public WebElement openColumnManagementDialog() {
    $("div[id$='task-widget-preview:dashboard-tasks-container']").shouldBe(appear, DEFAULT_TIMEOUT)
        .$("a[id$='column-toggler']").shouldBe(getClickableCondition(), DEFAULT_TIMEOUT).click();
    return getColumnManagementDialog().shouldBe(appear, DEFAULT_TIMEOUT);
  }

  public SelenideElement getColumnManagementDialog() {
    return $("div[id$='column-management-dialog']");
  }

  public void removeAddedField(String field) {
    SelenideElement removeLink = getAddedFieldRemoveLink(field);
    removeLink.click();
    removeLink.shouldBe(Condition.disappear, DEFAULT_TIMEOUT);
  }

  public SelenideElement getAddedFieldRemoveLink(String field) {
    return getColumnManagementDialog().$("tbody td.js-column-field-" + field + " a").shouldBe(getClickableCondition(), DEFAULT_TIMEOUT);
  }

  public void saveColumn() {
    getColumnManagementDialog().$("button[id$='column-management-save-btn']").click();
    $("div[id$=':column-management-dialog']").shouldBe(disappear, DEFAULT_TIMEOUT);
    waitPreviewTableLoaded();
  }

  private SelenideElement getMultiLanguageDialogWhenAddWidget() {
    return $("div[id='widget-configuration-form:new-widget-configuration-component:title-language-config:multiple-languages-dialog']");
  }
  public WebElement openMultiLanguageDialogWhenAddWidget() {
    getAddLanguageButton().click();
    getMultiLanguageDialogWhenAddWidget().shouldBe(Condition.appear, DEFAULT_TIMEOUT).$(".ui-dialog-title").shouldBe(appear, DEFAULT_TIMEOUT).click();
    return getMultiLanguageDialogWhenAddWidget();
  }
  
  public void cancelMultiLanguageDialogWhenAddWidget() {
    $("a[id$=':multi-language-cancel-button']").shouldBe(getClickableCondition(), DEFAULT_TIMEOUT).click();
    getMultiLanguageDialogWhenAddWidget().shouldBe(Condition.disappear, DEFAULT_TIMEOUT);
  }

  public SelenideElement getConfigurationDialog() {
    $("div[id='new-widget-configuration-dialog']").shouldBe(appear, DEFAULT_TIMEOUT)
      .$("[id='new-widget-configuration-dialog_title']").shouldBe(appear, DEFAULT_TIMEOUT).click();
    return $("div[id='new-widget-configuration-dialog']");
  }

  public void closeConfigurationDialog() {
    getConfigurationDialog().$(".ui-dialog-footer").$("a").shouldBe(getClickableCondition(), DEFAULT_TIMEOUT).click();
    $("div[id='new-widget-configuration-dialog']").shouldBe(disappear, DEFAULT_TIMEOUT);
  }
}
