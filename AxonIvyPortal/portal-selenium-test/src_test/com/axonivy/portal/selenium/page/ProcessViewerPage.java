package com.axonivy.portal.selenium.page;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Condition;

public class ProcessViewerPage extends TemplatePage{

  @Override
  protected String getLoadedLocator() {
    return "[id='portal-process-viewer-form']";
  }
  
  public String getProcessRequestPath() {
    $("[id='process-viewer-information'").shouldBe(Condition.appear, DEFAULT_TIMEOUT);
    return $("[id$='portal-process-viewer-form'] [id$='request-path']").getText();
  }
  
  public void waitForSprottyToolDisplayed() {
    switchToIframeWithId("process-viewer");
//    waitForIFrameContentVisible("process-viewer", 15000);
    $("[id='sprotty']").shouldBe(Condition.appear, DEFAULT_TIMEOUT);
    
//    waitForElementDisplayed(By.id("sprotty"), true); 
  }

}
