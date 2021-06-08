package portal.guitest.common;

public enum Variable {
  
  HIDE_LOGOUT_BUTTON("Portal.UserMenu.HideLogoutMenu"),
  HIDE_CHANGE_PASSWORD_BUTTON("Portal.UserMenu.HideChangePasswordMenu"),
  SHOW_ENVIRONMENT_INFO("Portal.ShowEnvironmentInfo"),
  ENABLE_SCRIPT_CHECKING_FOR_UPLOADED_DOCUMENT("Portal.Document.EnableScriptChecking"),
  UPLOAD_DOCUMENT_WHITELIST_EXTENSION("Portal.Document.WhitelistExtension"),
  HIDE_TIME("Portal.DateTimeFormat.HideTime"),
  HIDE_YEAR("Portal.DateTimeFormat.HideYear"),
  HIDE_SYSTEM_TASKS_FROM_HISTORY("Portal.Histories.HideSystemTasks"),
  HIDE_SYSTEM_TASKS_FROM_HISTORY_ADMINISTRATOR("Portal.Histories.HideSystemTasksForAdministrator"),
  HIDE_SYSTEM_NOTES_FROM_HISTORY("Portal.Histories.HideSystemNotes"),
  HIDE_SYSTEM_NOTES_FROM_HISTORY_ADMINISTRATOR("Portal.Histories.HideSystemNotesForAdministrator"),
  ENABLE_USER_FAVORITES("Portal.Dashboard.ShowUserFavorites"),
  HIDE_STATISTIC_WIDGET("Portal.Dashboard.HideStatisticWidget"),
  SHOW_USER_GUIDE("Portal.Dashboard.ShowUserGuide"),
  ENABLE_GROUP_CHAT("Portal.Chat.EnableGroup"),
  ENABLE_PRIVATE_CHAT("Portal.Chat.EnablePrivate"),
  CHAT_RESPONSE_TIMEOUT("Portal.Chat.ResponseTimeout"),
  CHAT_MAX_CONNECTION("Portal.Chat.MaxConnection"),
  ENABLE_CASE_OWNER("Portal.Cases.EnableOwner"),
  DISABLE_CASE_COUNT("Portal.Cases.DisableCount"),
  DEFAULT_SORT_FIELD_OF_CASE_LIST("Portal.Cases.SortField"),
  DEFAULT_SORT_DIRECTION_OF_CASE_LIST("Portal.Cases.SortDirection"),
  REFRESH_TASK_LIST_INTERVAL("Portal.Tasks.RefreshInterval"),
  DISABLE_TASK_COUNT("Portal.Tasks.DisableCount"),
  DEFAULT_SORT_FIELD_OF_TASK_LIST("Portal.Tasks.SortField"),
  DEFAULT_SORT_DIRECTION_OF_TASK_LIST("Portal.Tasks.SortDirection"),
  SHOW_TASK_DURATION_TIME("Portal.TaskDetails.ShowDurationTime"),
  HIDE_TASK_DOCUMENT("Portal.TaskDetails.HideDocument"),
  HIDE_CASE_DOCUMENT("Portal.CaseDetails.HideDocument"),
  SHOW_CASE_DURATION_TIME("Portal.CaseDetails.ShowDurationTime"),
  HIDE_UPLOAD_DOCUMENT_FOR_DONE_CASE("Portal.CaseDetails.HideUploadDocumentForDoneCase"),
  DEFAULT_PROCESS_MODE("Portal.Processes.Mode"),
  DISPLAY_MESSAGE_AFTER_FINISH_TASK("Portal.DisplayMessageAfterFinishTask"),
  EXPRESS_END_PAGE("Portal.ExpressEndPage"),
  CLIENT_SIDE_TIMEOUT("Portal.ClientSideTimeout"),
  EMBED_IN_FRAME("Portal.EmbedInFrame"),
  LOGGED_IN_USER_FORMAT("Portal.LoggedInUserFormat"),
  SHOW_GLOBAL_SEARCH("Portal.ShowGlobalSearch"),
  SHOW_BUTTON_ICON("Portal.ShowButtonIcon"),
  DEFAULT_HOMEPAGE("Portal.Homepage"),
  DISPLAY_USERS_OF_TASK_ACTIVATOR("Portal.DisplayUsersOfRole");

  private String key;

  private Variable(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

}
