package ch.ivy.addon.portalkit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import ch.ivy.addon.portalkit.constant.IvyCacheIdentifier;
import ch.ivy.addon.portalkit.ivydata.service.IApplicationService;
import ch.ivy.addon.portalkit.persistence.dao.ApplicationDao;
import ch.ivy.addon.portalkit.persistence.domain.Application;
import ch.ivy.addon.portalkit.persistence.domain.User;

public class ApplicationService extends AbstractService<Application> {

  public ApplicationService() {
    super(ApplicationDao.class);
  }

  @Override
  protected ApplicationDao getDao() {
    return (ApplicationDao) super.getDao();
  }

  public List<Application> findAllThirdPartyApplications() {
    return getDao().findAllThirdPartyApplications();
  }

  public List<Application> findAllIvyApplications() {
    return getDao().findAllIvyApplications();
  }

  public List<Application> findOtherApplicationsHaveSameNameAndServer(Application application) {
    return getDao().findOtherApplicationsHaveSameNameAndServer(application);
  }

  public Application findByDisplayNameAndName(String displayName, String name) {
    return getDao().findByDisplayNameAndName(displayName, name);
  }

  public List<Application> findOnlineIvyApps() {
    return getDao().findOnlineIvyApps();
  }

  public Application findByName(String name) {
    return getDao().findByName(name);
  }

  public List<Application> findByNames(List<String> names) {
    return getDao().findByNames(names);
  }

  public String convertApplicationIdsToString(List<Long> applicationIds) {
    if (CollectionUtils.isEmpty(applicationIds)) {
      return StringUtils.EMPTY;
    }
    return applicationIds.stream().map(String::valueOf).collect(Collectors.joining(","));
  }

  public long countIvyApplications(List<Application> applications) {
    return applications.stream().filter(application -> application.getServerId() != null).count();
  }
  
  /**
   * Finds names of the online and visible applications registered by the admin user; if empty, means there is no
   * configuration in admin settings, finds all of active applications of the engine.
   * 
   * @param username
   * @return {@link java.util.List} of application names
   */
  @SuppressWarnings("unchecked")
  public List<String> findActiveIvyAppsUserCanWorkOn(String username) {
    if (StringUtils.isBlank(username)) {
      return new ArrayList<>();
    }
    
    Optional<Object> cacheValueOpt = IvyCacheService.newInstance().getSessionCacheValue(username, IvyCacheIdentifier.ONLINE_APPLICATIONS_USER_CAN_WORK_ON);
    if (cacheValueOpt.isPresent()) {
      return (List<String>) cacheValueOpt.get();
    }
    
    List<User> users = new UserService().findByUserName(username);
    if (users == null) {
      return new ArrayList<>();
    }

    List<String> appNames =
        users.stream().map(User::getApplicationName).filter(StringUtils::isNotBlank).collect(Collectors.toList());
    List<String> workOnApps = findActiveIvyAppsBy(appNames);
    IvyCacheService.newInstance().setSessionCache(username, IvyCacheIdentifier.ONLINE_APPLICATIONS_USER_CAN_WORK_ON, workOnApps);
    return workOnApps;
  }
  
  /**
   * Finds names of the online and visible applications registered by the admin user; if empty, means there is no
   * configuration in admin settings, finds all of active applications of the engine.
   * 
   * @param appNames
   * @return
   */
  public List<String> findActiveIvyAppsBy(List<String> appNames) {
    List<String> workOnApps = new ArrayList<>();
    List<Application> applications = findOnlineAndVisibleIvyAppsBy(appNames);
    if (CollectionUtils.isNotEmpty(applications)) {
      workOnApps = applications.stream().map(Application::getName).collect(Collectors.toList());
    } else {
      IApplicationService applicationService = ch.ivy.addon.portalkit.ivydata.service.impl.ApplicationService.newInstance();
      workOnApps = applicationService.findActiveAll().stream().map(ch.ivy.addon.portalkit.ivydata.bo.IvyApplication::getName)
          .collect(Collectors.toList());
    }
    
    return workOnApps;
  }
  
  public List<Application> findOnlineAndVisibleIvyAppsBy(List<String> appNames) {
    return getDao().findOnlineAndVisibleIvyAppsBy(appNames);
  }
  
  /**
   * Finds names of the absence enable and online and visible applications registered by the admin user; if empty, means there is no
   * configuration in admin settings, finds all of active applications of the engine.
   * 
   * @param username
   * @return {@link java.util.List} of application names
   */
  @SuppressWarnings("unchecked")
  public List<String> findAbsenceEnableAndActiveIvyAppsUserCanWorkOn(String username) {
    if (StringUtils.isBlank(username)) {
      return new ArrayList<>();
    }
    
    Optional<Object> cacheValueOpt = IvyCacheService.newInstance().getSessionCacheValue(username, IvyCacheIdentifier.ABSENCE_ENABLE_AND_ONLINE_APPLICATIONS);
    if (cacheValueOpt.isPresent()) {
      return (List<String>) cacheValueOpt.get();
    }
    
    List<User> users = new UserService().findByUserName(username);
    if (users == null) {
      return new ArrayList<>();
    }

    List<String> workOnApps = new ArrayList<>();
    List<String> appNames =
        users.stream().map(User::getApplicationName).filter(StringUtils::isNotBlank).collect(Collectors.toList());
    List<Application> applications = getDao().findAbsenceEnableAndOnlineAndVisibleIvyAppsBy(appNames);
    if (CollectionUtils.isNotEmpty(applications)) {
      workOnApps = applications.stream().map(Application::getName).collect(Collectors.toList());
    } else {
      IApplicationService applicationService = ch.ivy.addon.portalkit.ivydata.service.impl.ApplicationService.newInstance();
      workOnApps = applicationService.findActiveAll().stream().map(ch.ivy.addon.portalkit.ivydata.bo.IvyApplication::getName)
          .collect(Collectors.toList());
    }

    IvyCacheService.newInstance().setSessionCache(username, IvyCacheIdentifier.ABSENCE_ENABLE_AND_ONLINE_APPLICATIONS, workOnApps);
    return workOnApps;
  }
}
