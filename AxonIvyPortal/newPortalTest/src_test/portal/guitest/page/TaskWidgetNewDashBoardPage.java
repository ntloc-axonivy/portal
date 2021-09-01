package portal.guitest.page;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

public class TaskWidgetNewDashBoardPage extends TemplatePage {

  private static final String YOUR_TASKS_WIDGET = "Your Tasks";
  private static final String FILTER_TASK_NAME = "Task name";

  private String taskWidgetId;
  private String taskWidgetName;

  public TaskWidgetNewDashBoardPage() {
    this("div[id$='dashboard-tasks']", YOUR_TASKS_WIDGET);
  }

  public TaskWidgetNewDashBoardPage(String taskWidgetName) {
    this("div[id$='dashboard-tasks']", taskWidgetName);
  }

  public TaskWidgetNewDashBoardPage(String taskWidgetId, String taskWidgetName) {
    this.taskWidgetId = taskWidgetId;
    this.taskWidgetName = taskWidgetName;
  }

  private int getIndexWidgetByColumn(String columnName) {
    ElementsCollection elementsTH = $(taskWidgetId).waitUntil(appear, DEFAULT_TIMEOUT).$$("table thead tr th");
    for (int i = 0; i < elementsTH.size(); i++) {
      if (elementsTH.get(i).getText().equalsIgnoreCase(columnName)) {
        return i;
      }
    }
    return 0;
  }

  private ElementsCollection getColumnsOfTableWidget() {
    return $(taskWidgetId).waitUntil(appear, DEFAULT_TIMEOUT).$$("table tbody tr td");
  }

  private ElementsCollection getColumnOfTableWidget(int rowIndex) {
    return $(taskWidgetId).waitUntil(appear, DEFAULT_TIMEOUT).$$("table tbody tr").get(rowIndex).$$("td");
  }

  public ElementsCollection expand() {
    return $$("div.widget__header").filter(text(taskWidgetName));
  }

  private ElementsCollection getTasksOfTaskWidgetHasName(String caseName) {
    return getColumnsOfTableWidget().filter(text(caseName));
  }

  private SelenideElement getTaskOfTaskWidgetHasIndex(int index) {
    return getColumnsOfTableWidget().get(index);
  }

  private SelenideElement getColumnOfTaskHasIndex(int index, String columnName) {
    int startIndex = getIndexWidgetByColumn(columnName);
    return getColumnOfTableWidget(index).get(startIndex);
  }

  public void startFirstTask() {
    getColumnOfTaskHasIndex(0, "Start").shouldBe(getClickableCondition()).click();
  }

  public ElementsCollection countRelatedCases() {
    return $("div[id$='related-cases']").$$("td.name-column");
  }

  public void openFilterWidget() {
    $$("form.table-widget-form").filter(text(taskWidgetName)).first().$("a.widget__filter-sidebar-link")
        .waitUntil(appear, DEFAULT_TIMEOUT).shouldBe(getClickableCondition()).click();
  }

  private SelenideElement getFilterInput(String inputField) {
    return $("div[id$='widget-filter-content']").waitUntil(appear, DEFAULT_TIMEOUT)
        .$$("div.widget-filter-panel div.ui-g").filter(text(inputField)).first().$("input.ui-inputfield");
  }

  public void filterTaskName(String input) {
    getFilterInput(FILTER_TASK_NAME).sendKeys(input);
  }

  private SelenideElement getFilterCheckBox(String inputField) {
    return $("div[id$='widget-filter-content']").waitUntil(appear, DEFAULT_TIMEOUT)
        .$$("div.widget-filter-panel div.ui-g").filter(text(inputField)).first();
  }

  private SelenideElement getCloseCheckBox() {
    return $("div.ui-selectcheckboxmenu-panel").waitUntil(appear, DEFAULT_TIMEOUT).$("a.ui-selectcheckboxmenu-close");
  }

  private SelenideElement getValueOfCheckBox(String value) {
    return $("div.ui-selectcheckboxmenu-items-wrapper").waitUntil(appear, DEFAULT_TIMEOUT)
        .$$("li.ui-selectcheckboxmenu-item").filter(text(value)).first().$("div.ui-chkbox-box");
  }

  public void applyFilter() {
    $("div.filter-overlay-panel__footer").waitUntil(appear, DEFAULT_TIMEOUT).$$("button[id$='apply-button']")
        .filter(text("Apply")).first().shouldBe(getClickableCondition()).click();
  }

}
