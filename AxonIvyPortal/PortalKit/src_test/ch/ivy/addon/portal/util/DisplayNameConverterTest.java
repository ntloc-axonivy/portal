package ch.ivy.addon.portal.util;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

import ch.ivy.addon.portalkit.util.DisplayNameConvertor;

public class DisplayNameConverterTest {
  
  @Test
  public void testGetDisplayNameAsMapWhenHaveNoDisplayName() {
    DisplayNameConvertor displayNameConvertor = new DisplayNameConvertor();
    assertEquals(displayNameConvertor.getDisplayNameAsMap().size(), 0);
  }
  
  @Test
  public void testGetDisplayNameAsMapAfterAddingDisplayName() {
    DisplayNameConvertor displayNameConvertor = new DisplayNameConvertor();
    String appName ="InternalSupport";
    Locale locale = new Locale("en");
    displayNameConvertor.add(locale, appName);
    assertEquals(displayNameConvertor.getDisplayNameAsMap().size(), 1);
  }

}
