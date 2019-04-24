package portal.guitest.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LanguagePage extends TemplatePage {
  
  @Override
  protected String getLoadedLocator() {
    return "id('language-setting-dialog_title')";
  }


	public void selectLanguage(int newLanguage) {
	   waitForElementDisplayed(By.cssSelector("div[id$='selection'] div.ui-selectonemenu-trigger"), true);
	   findElementByCssSelector("div[id$='selection'] div.ui-selectonemenu-trigger").click();
	   findElementByCssSelector("li[id$='selection_"+newLanguage+"']").click(); 
	}
  public void save() {
    WebElement save = findElementById("language-settings-form:language-settings:save-settings");
    save.click();
  }
}
