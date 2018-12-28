package ch.ivy.addon.portalkit.support;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.boon.datarepo.Repo;

import ch.ivy.addon.portalkit.persistence.domain.Application;
import ch.ivy.addon.portalkit.persistence.domain.User;
import ch.ivy.addon.portalkit.persistence.variable.PortalCacheConstants;
import ch.ivy.addon.portalkit.service.ApplicationService;
import ch.ivy.addon.portalkit.service.GlobalSettingService;
import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.data.cache.IDataCache;
import ch.ivyteam.ivy.data.cache.IDataCacheEntry;
import ch.ivyteam.ivy.data.cache.IDataCacheGroup;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.server.ServerFactory;

public final class DataCache {
  
  public static Object getGlobalSettingFromCache(String attributeName){
    IDataCacheEntry entry = Ivy.datacache().getAppCache().getEntry(PortalCacheConstants.GLOBAL_SETTING_CACHE_GROUP_NAME, attributeName);
    return entry == null ? null : entry.getValue();
  }
  
  public static void cacheGlobalSetting(String name, Object value){
    Ivy.datacache().getAppCache().setEntry(PortalCacheConstants.GLOBAL_SETTING_CACHE_GROUP_NAME, name, value);
  }
  
  public static void invalidateGlobalSettingCache(){
    IDataCacheGroup wsGroupNameCurrentApp = Ivy.datacache().getAppCache().getGroup(PortalCacheConstants.GLOBAL_SETTING_CACHE_GROUP_NAME);
    if (wsGroupNameCurrentApp != null){
      Ivy.log().info("CLEAR GET APPS WS CACHE CURRENT APP");
      Ivy.datacache().getAppCache().invalidateGroup(wsGroupNameCurrentApp );
    }
    invalidateGlobalSettingOnApp(IApplication.PORTAL_APPLICATION_NAME);
    ApplicationService service = new ApplicationService();
    List<Application> findAllIvyApplications = service.findAllIvyApplications();
    findAllIvyApplications.forEach(application -> invalidateGlobalSettingOnApp(application.getName()));
  }

  private static void invalidateGlobalSettingOnApp(String applicationName) {
    try {
      ServerFactory.getServer().getSecurityManager().executeAsSystem(() ->{
        IApplication findApplication = ServerFactory.getServer().getApplicationConfigurationManager().findApplication(applicationName);
        if (findApplication != null) {
          IDataCache cache = (IDataCache) findApplication .getAdapter(IDataCache.class);
          if (cache != null) {
            IDataCacheGroup wsGroupName = cache.getGroup(PortalCacheConstants.GLOBAL_SETTING_CACHE_GROUP_NAME);
            if (wsGroupName != null){
              Ivy.log().info("CLEAR GLOBAL SETTING CACHE : {0} on application {1}", PortalCacheConstants.GLOBAL_SETTING_CACHE_GROUP_NAME, applicationName);
              wsGroupName.invalidateAllEntries();
            }
          }
        }
        return null;
      });
    } catch (Exception e) {
      Ivy.log().error(e);
    }
  }

  public static String getGlobalSettingValueAsString(String attributeName){
    Object attribute = getGlobalSettingFromCache(attributeName);
    if (attribute == null){
      String attributeValue = getValueFromDB(attributeName);
      cacheGlobalSetting(attributeName, attributeValue);
      return attributeValue;      
    }
    return String.valueOf(attribute);
  }
  
  public static Boolean getGlobalSettingValueAsBoolean(String attributeName){
    Object attribute = getGlobalSettingFromCache(attributeName);
    if (attribute == null){
      String attributeValue = getValueFromDB(attributeName);
      cacheGlobalSetting(attributeName, attributeValue);
      return Boolean.valueOf(attributeValue);      
    }
    return Boolean.valueOf((String)attribute);
  }

  private static String getValueFromDB(String attributeName) {
    GlobalSettingService globalSettingSerive = new GlobalSettingService();
    String attributeValue = globalSettingSerive.findGlobalSettingValue(attributeName);
    return attributeValue;
  }
  
  public static void cacheLogoutPage(String logoutUrl){
    Ivy.datacache().getSessionCache().setEntry(PortalCacheConstants.LOGOUT_PAGE_CACHE_GROUP_NAME, PortalCacheConstants.LOGOUT_PAGE_CACHE_ENTRY_NAME, logoutUrl);
  }
  
  public static String getLogoutPageFromCache(){
    IDataCacheEntry entry = Ivy.datacache().getSessionCache().getEntry(PortalCacheConstants.LOGOUT_PAGE_CACHE_GROUP_NAME, PortalCacheConstants.LOGOUT_PAGE_CACHE_ENTRY_NAME);
    return entry == null ? StringUtils.EMPTY : String.valueOf(entry.getValue());
  }
  
  @SuppressWarnings("unchecked")
  public static List<User> getAllUsersFromCache() {
    IDataCacheEntry cacheEntry = Ivy.datacache().getAppCache().getEntry(PortalCacheConstants.PORTAL_USERS_CACHE_GROUP_NAME, PortalCacheConstants.USERS_LIST_CACHE_ENTRY_NAME);
    return cacheEntry != null ? (List<User>) cacheEntry.getValue() : null;
  }
  
  public static void cacheAllUsers(IDataCache cache, String applicationName, List<User> users) {
    Ivy.log().info("User list didn't exist in cache of application " + applicationName + ", store in cache now with number of inserted users = " +users.size());
    cache.setEntry(PortalCacheConstants.PORTAL_USERS_CACHE_GROUP_NAME, PortalCacheConstants.USERS_LIST_CACHE_ENTRY_NAME, users);
  }
  
  @SuppressWarnings("unchecked")
  public static Repo<Long, User> getUserRepoFromCache() {
      IDataCacheEntry cacheEntry = Ivy.datacache().getAppCache().getEntry(PortalCacheConstants.PORTAL_USERS_CACHE_GROUP_NAME, PortalCacheConstants.USERS_REPO_CACHE_ENTRY_NAME);
      return cacheEntry != null ? (Repo<Long, User>) cacheEntry.getValue() : null;
    }
  
  public static void cacheUsersRepo(IDataCache cache, String applicationName, Repo<Long, User> repo) {
    Ivy.log().info("User repo didn't exist in cache of application " + applicationName + ", store in cache now");
    cache.setEntry(PortalCacheConstants.PORTAL_USERS_CACHE_GROUP_NAME, PortalCacheConstants.USERS_REPO_CACHE_ENTRY_NAME, repo);
  }
  
  public static void invalidateUsersCache(IDataCache cache, String applicationName) {
    Ivy.log().info("Invalidated users cache in application: " + applicationName);
    IDataCacheGroup group = cache.getGroup(PortalCacheConstants.PORTAL_USERS_CACHE_GROUP_NAME);
    if (group != null) {
      cache.invalidateGroup(group);
    }
  }
}
