package ch.ivy.addon.portal.generic.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;

import ch.ivy.addon.portalkit.constant.UserProperty;
import ch.ivy.addon.portalkit.enums.CaseSortField;
import ch.ivy.addon.portalkit.enums.GlobalVariable;
import ch.ivy.addon.portalkit.enums.SortDirection;
import ch.ivy.addon.portalkit.enums.TaskSortField;
import ch.ivy.addon.portalkit.ivydata.service.impl.UserSettingService;
import ch.ivy.addon.portalkit.service.GlobalSettingService;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.security.IUser;

@ManagedBean
@ViewScoped
public class UserProfileBean implements Serializable {
  private static final String DEFAULT_OPTION = "/ch.ivy.addon.portalkit.ui.jsf/MyProfile/defaultOption";
  private static final long serialVersionUID = 4952280551311826903L;
  private static final String DEFAULT = UserSettingService.DEFAULT;
  
  public void saveHomepage(String homepageName) {
    IUser user = Ivy.session().getSessionUser();
    if (StringUtils.isBlank(homepageName)) {
      user.removeProperty(UserProperty.HOMEPAGE);
    }
    user.setProperty(UserProperty.HOMEPAGE, homepageName);
  }
  
  public String getDisplayNameOfTaskSortField(String sortField) {
    if (StringUtils.equals(sortField, DEFAULT)) {
      return getDefaultSelection(GlobalVariable.DEFAULT_SORT_FIELD_OF_TASK_LIST);
    }

    List<String> sortFieldNames = Stream.of(TaskSortField.values()).map(Enum::name).collect(Collectors.toList());
    return sortFieldNames.contains(sortField) ? TaskSortField.valueOf(sortField).getLabel() : sortField;
  }

  public String getDisplayNameOfCaseSortField(String sortField) {
    if (StringUtils.equals(sortField, DEFAULT)) {
      return getDefaultSelection(GlobalVariable.DEFAULT_SORT_FIELD_OF_CASE_LIST);
    }

    List<String> sortFieldNames = Stream.of(CaseSortField.values()).map(Enum::name).collect(Collectors.toList());
    return sortFieldNames.contains(sortField) ? CaseSortField.valueOf(sortField).getLabel() : sortField;
  }

  public String getDisplayNameOfTaskSortDirection(String sortDirection) {
    if (StringUtils.equals(sortDirection, DEFAULT)) {
      return getDefaultSelection(GlobalVariable.DEFAULT_SORT_DIRECTION_OF_TASK_LIST);
    }

    List<String> sortDirectionNames = Stream.of(SortDirection.values()).map(Enum::name).collect(Collectors.toList());
    return sortDirectionNames.contains(sortDirection) ? SortDirection.valueOf(sortDirection).getLabel() : "";
  }

  public String getDisplayNameOfCaseSortDirection(String sortDirection) {
    if (StringUtils.equals(sortDirection, DEFAULT)) {
      return getDefaultSelection(GlobalVariable.DEFAULT_SORT_DIRECTION_OF_CASE_LIST);
    }

    List<String> sortDirectionNames = Stream.of(SortDirection.values()).map(Enum::name).collect(Collectors.toList());
    return sortDirectionNames.contains(sortDirection) ? SortDirection.valueOf(sortDirection).getLabel() : "";
  }

  private String getDefaultSelection(GlobalVariable defaultOption) {
    GlobalSettingService globalSettingService = new GlobalSettingService();
    return Ivy.cms().co(DEFAULT_OPTION, Arrays.asList(globalSettingService.findGlobalSettingByKey(defaultOption.name()).getDisplayValue()));
  }

  public String getDisplayDatePattern(String selectedPattern) {
    String datePattern =
        StringUtils.equals(selectedPattern, DEFAULT) ? UserSettingService.newInstance().getDefaultDateFormat()
            : selectedPattern;
    String displayDate = prepareDisplayCurrentDate(datePattern);
    return StringUtils.equals(selectedPattern, DEFAULT)
        ? Ivy.cms().co(DEFAULT_OPTION, Arrays.asList(UserSettingService.newInstance().getDefaultDateFormat()))
            + displayDate
        : datePattern + displayDate;
  }

  public String prepareDisplayCurrentDate(String pattern) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
    return " (" + dateFormat.format(new Date()) + ")";
  }
}