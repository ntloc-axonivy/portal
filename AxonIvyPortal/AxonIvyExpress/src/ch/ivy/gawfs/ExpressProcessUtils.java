package ch.ivy.gawfs;

import gawfs.TaskDef;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import ch.ivy.addon.portalkit.bo.ExpressFormElement;
import ch.ivy.addon.portalkit.bo.ExpressProcess;
import ch.ivy.addon.portalkit.bo.ExpressTaskDefinition;
import ch.ivy.addon.portalkit.dto.ExpressAttachment;
import ch.ivy.addon.portalkit.enums.ExpressEmailAttachmentStatus;
import ch.ivy.addon.portalkit.service.ExpressServiceRegistry;
import ch.ivy.gawfs.enums.FormElementType;
import ch.ivy.gawfs.enums.ProcessType;
import ch.ivy.gawfs.enums.TaskType;
import ch.ivy.gawfs.mail.MailAttachment;
import ch.ivyteam.ivy.business.data.store.BusinessDataInfo;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.security.IRole;
import ch.ivyteam.ivy.security.ISecurityMember;
import ch.ivyteam.ivy.security.IUser;

public class ExpressProcessUtils {

  private static final String HEADER_PANEL = "HEADER";
  private static final String FOOTER_PANEL = "FOOTER";
  private static final String LEFT_PANEL = "LEFTPANEL";
  private static final String RIGHT_PANEL = "RIGHTPANEL";

  /**
   * Save Express process to repository as business data
   * 
   * @param processId
   * @param name
   * @param description
   * @param type
   * @param processFolder
   * @param isUseDefaultUI
   * @param definedTasks
   * 
   * @return Express process after saved
   */
  public ExpressProcess saveProcess(String processId, String name, String description, ProcessType type, String processFolder, boolean isUseDefaultUI, List<TaskDef> definedTasks) {
    ExpressProcess processRepository
      = Optional.ofNullable(ExpressServiceRegistry.getProcessService().findById(processId)).orElse(new ExpressProcess());

    processRepository.setProcessName(name);
    processRepository.setProcessDescription(description);
    processRepository.setProcessType(type.getValue());
    processRepository.setUseDefaultUI(isUseDefaultUI);
    processRepository.setProcessOwner(Ivy.session().getSessionUser().getMemberName());
    processRepository.setProcessPermissions(definedTasks.get(0).getResponsibles());
    processRepository.setProcessFolder(processFolder);

    BusinessDataInfo<ExpressProcess> info = ExpressServiceRegistry.getProcessService().save(processRepository);
    processRepository.setId(info.getId());

    saveDefinedTasks(processRepository.getId(), definedTasks);

    return processRepository;
  }

  /**
   * Save defined tasks to repository as business data
   * 
   * @param processId
   * @param definedTasks
   */
  private void saveDefinedTasks(String processId, List<TaskDef> definedTasks) {
    // Delete old tasks and form elements
    ExpressServiceRegistry.getTaskDefinitionService().deleteByProcessId(processId);
    ExpressServiceRegistry.getFormElementService().deleteByProcessId(processId);

    // Save the task definition with the order of the tasks
    for (TaskDef taskDef: definedTasks){
        ExpressTaskDefinition expressTaskDef = new ExpressTaskDefinition();
        expressTaskDef.setType(taskDef.getTaskType().name());
        expressTaskDef.setSubject(taskDef.getSubject());
        expressTaskDef.setDescription(taskDef.getDescription());
        expressTaskDef.setResponsibles(taskDef.getResponsibles());
        expressTaskDef.setUntilDays(taskDef.getUntilDays().intValue());
        expressTaskDef.setProcessID(processId);
        expressTaskDef.setTaskPosition(taskDef.getPosition());
        expressTaskDef.setEmail(taskDef.getEmail());
        ExpressServiceRegistry.getTaskDefinitionService().save(expressTaskDef);
        if(taskDef.getTaskType() != TaskType.EMAIL && taskDef.getTaskType() != TaskType.APPROVAL) {
        	saveFormElements(processId, taskDef.getPosition(), taskDef.getDragAndDropController());
        }
    }
  }

  /**
   * Save all form elements with Id, location, and order
   * 
   * @param processId
   * @param taskPosition
   * @param controller
   */
  private void saveFormElements(String processId, int taskPosition, DragAndDropController controller) {
    for (Formelement element : controller.getSelectedFormelementsHeader()) {
      element.setTaskPosition(taskPosition);
      saveFormElement(element, HEADER_PANEL, processId);
    }

    for (Formelement element : controller.getSelectedFormelementsLeftPanel()) {
      element.setTaskPosition(taskPosition);
      saveFormElement(element, LEFT_PANEL, processId);
    }

    for (Formelement element : controller.getSelectedFormelementsRightPanel()) {
      element.setTaskPosition(taskPosition);
      saveFormElement(element, RIGHT_PANEL, processId);
    }

    for (Formelement element : controller.getSelectedFormelementsFooter()) {
      element.setTaskPosition(taskPosition);
      saveFormElement(element, FOOTER_PANEL, processId);
    }
  }

  /**
   * Save form element to repository as business data
   * 
   * @param element
   * @param location
   * @param processId
   */
  private void saveFormElement(Formelement element, String location, String processId) {
    ExpressFormElement expressFormElement = new ExpressFormElement();
    expressFormElement.setElementID(element.getId());
    expressFormElement.setElementPosition(location);
    expressFormElement.setElementType(element.getType().getValue());
    expressFormElement.setIntSetting(Optional.ofNullable(element.getIntSetting()).orElse(0));
    expressFormElement.setLabel(element.getLabel());
    expressFormElement.setRequired(Optional.ofNullable(element.getRequired()).orElse(false));
    expressFormElement.setProcessID(processId);
    expressFormElement.setOptionsStr(element.getOptionsAsString());
    expressFormElement.setTaskPosition(element.getTaskPosition());

    ExpressServiceRegistry.getFormElementService().save(expressFormElement);
  }

  /**
   * Get defined tasks by process ID from repository
   * 
   * @param processId
   * @return List of defined tasks
   */
  public List<TaskDef> getDefinedTasks(String processId) {
    List<TaskDef> taskDefinitions = new ArrayList<>();
    List<ExpressTaskDefinition> expressTaskDefinitions = ExpressServiceRegistry.getTaskDefinitionService().findByProcessId(processId);

    for (ExpressTaskDefinition expressTaskDef : expressTaskDefinitions) {
      TaskDef taskDef = new TaskDef();
      taskDef.setResponsibles(getValidSecurityMembers(expressTaskDef.getResponsibles()));
      taskDef.setTaskType(EnumUtils.getEnum(TaskType.class, expressTaskDef.getType()));
      taskDef.setPosition(expressTaskDef.getTaskPosition());
      taskDef.setDescription(expressTaskDef.getDescription());
      taskDef.setSubject(expressTaskDef.getSubject());
      taskDef.setUntilDays(expressTaskDef.getUntilDays());
      taskDef.setResponsibleDisplayName(expressTaskDef.getResponsibleDisplayName());
      taskDef.setEmail(expressTaskDef.getEmail());
      taskDef.setResponsibleDisplayName(generateResponsibleDisplayName(taskDef.getResponsibles()));

      initializeControllersForTaskDef(processId, taskDef);
      taskDefinitions.add(taskDef);
    }

    return Helper.sortTasks(taskDefinitions);
  }

  /**
   * Get merged display name of responsibles
   * 
   * @param responsibleNames
   * @return merged display name
   */
  private String generateResponsibleDisplayName(List<String> responsibleNames) {
    List<String> responsibleDisplayNames = new ArrayList<>();
    responsibleNames.forEach(responsibleName -> {
      ISecurityMember responsible = Ivy.session().getSecurityContext().findSecurityMember(responsibleName);
      if (!StringUtils.isBlank(responsible.getDisplayName())) {
        responsibleDisplayNames.add(responsible.getDisplayName());
      } else {
        responsibleDisplayNames.add(responsible.getName());
      }
    });

    return String.join(", ", responsibleDisplayNames);
  }

  /**
   * Get valid security members based on responsible names
   * 
   * @param responsibleNames
   * @return security members
   */
  public List<String> getValidSecurityMembers(List<String> responsibleNames) {
    List<String> securityMembers = responsibleNames;
    for (String responsibleName : responsibleNames) {
      ISecurityMember securityMember = Ivy.session().getSecurityContext().findSecurityMember(responsibleName);
      if (securityMember == null) {
        responsibleNames.remove(responsibleName);
      }
      else if(securityMember.isUser()) {
    	  IUser iuser = (IUser) securityMember;
    	  iuser.getEMailAddress();
      }
    }
    return securityMembers;
  }

  /**
   * get email addresses from responsible names
   * @param responsibleNames
   * @return email addresses
   */
  public List<String> getRecipientEmailAddresses(List<String> responsibleNames){
    if (responsibleNames == null) {
      return Collections.emptyList();
    }
	    List<String> emailAddresses = new ArrayList<>();
	    for (String responsibleName : responsibleNames) {
	      ISecurityMember securityMember = Ivy.session().getSecurityContext().findSecurityMember(responsibleName);
	      if (securityMember != null) {
	    	  getEmailAddressFromSecurityMemeber(emailAddresses, securityMember);
	      }
	      
	    }
	    return emailAddresses;
  }

  private void getEmailAddressFromSecurityMemeber(List<String> emailAddresses, ISecurityMember securityMember) {
    if(securityMember.isUser()) {
      IUser iuser = (IUser) securityMember;
      if(StringUtils.isNoneBlank(iuser.getEMailAddress())){
    	  emailAddresses.add(iuser.getEMailAddress());
      }
    }
    else{
      IRole irole = (IRole) securityMember;
      for(IUser userInRole : irole.getUsers()){
    	  if(StringUtils.isNoneEmpty(userInRole.getEMailAddress())){
    		  emailAddresses.add(userInRole.getEMailAddress());
    	  }
      }
    }
  }
  
  /**
   * Initialize controllers for task definition
   * 
   * @param processId
   * @param taskDef
   * @return initialized task definition
   */
  private TaskDef initializeControllersForTaskDef(String processId, TaskDef taskDef) {
    DragAndDropController dragAndDropController = new DragAndDropController();
    DynaFormController dynaFormController = new DynaFormController(dragAndDropController);
    dragAndDropController.setDynaFormController(dynaFormController);
    dynaFormController.createForm();

    taskDef.setDynaFormController(dynaFormController);
    taskDef.setDragAndDropController(dragAndDropController);
    updateDragAndDropController(processId, taskDef.getDragAndDropController(), taskDef.getPosition());

    return taskDef;
  }

  /**
   * Update controller with form elements get from repository by process ID and task position
   * 
   * @param processId
   * @param controller
   */
  private void updateDragAndDropController(String processId, DragAndDropController controller, int taskPosition) {
    List<ExpressFormElement> expressFormElements = ExpressServiceRegistry.getFormElementService().findByProcessId(processId);
    expressFormElements = expressFormElements.stream().filter(element -> element.getTaskPosition() == taskPosition).collect(Collectors.toList());

    for (ExpressFormElement expressElement: expressFormElements){
      Formelement element = new Formelement();
      element.setId(expressElement.getElementID());
      element.setIntSetting(expressElement.getIntSetting());
      element.setLabel(expressElement.getLabel());
      element.setRequired(expressElement.isRequired());
      element.setTaskPosition(taskPosition);

      for (FormElementType type : FormElementType.values()) {
        if (expressElement.getElementType().equals(type.getValue())) {
          element.setType(type);
        }
      }

      String[] optionsArray = expressElement.getOptionsStr().split(":",-1);
      for (String optionStr : optionsArray) {
        element.addOption(optionStr);
      }

      String location = expressElement.getElementPosition();
      switch (location) {
        case HEADER_PANEL:
          controller.getSelectedFormelementsHeader().add(element);
          break;
        case LEFT_PANEL:
          controller.getSelectedFormelementsLeftPanel().add(element);
          break;
        case RIGHT_PANEL:
          controller.getSelectedFormelementsRightPanel().add(element);
          break;
        case FOOTER_PANEL:
          controller.getSelectedFormelementsFooter().add(element);
          break;
        default:
          break;
      }
    }
  }
  
  public boolean isNeedUpdatePathForAttachments(List<TaskDef> taskDefs) {
	  for(TaskDef task : taskDefs) {
		  if(task.getEmail() != null && task.getEmail().getAttachments() != null) {
			  for(ExpressAttachment attachment : task.getEmail().getAttachments()){
			    return attachment.getPath() == null;
			  }
		  }
	  }
	  return false;
  }
  
  public String generateProcessFolder(){
	  return UUID.randomUUID().toString();
  }
  
  public void saveAttachments(String folder, List<TaskDef> taskDefs){
	  String folderPath = "/Express/Process/" + folder + "/Attachment/";
	  for(TaskDef task : taskDefs) {
		  if(task.getEmail() != null && task.getEmail().getAttachments() != null) {
			  List<ExpressAttachment> attachments = task.getEmail().getAttachments();
			  setPathForAttachments(folderPath, attachments);
			  MailAttachment mailAttachment = new MailAttachment(attachments);
			  mailAttachment.updatePhysicalPaths();
			  removeDeletedAttachment(attachments);
		  }
	  }
  }

  private void setPathForAttachments(String folderPath, List<ExpressAttachment> attachments) {
    for(ExpressAttachment attachment : attachments){
      if(attachment.getPath() == null && attachment.getContent() != null) {
    	  attachment.setPath(folderPath + attachment.getName());
      }
    }
  }
  
  private void removeDeletedAttachment(List<ExpressAttachment> attachments) {
	  Iterator<ExpressAttachment> attachmentIter = attachments.iterator();
	  if(attachmentIter.hasNext()) {
		  ExpressAttachment attachment = attachmentIter.next();
		  if(attachment.getStatus() == ExpressEmailAttachmentStatus.DELETED || attachment.getPath() == null) {
			  attachmentIter.remove();
		  }
	  }
  }

  /**
   * Check whether a task is user task or not
   * 
   * @param task
   * @return check result
   */
  public boolean isUserTask(TaskDef task) {
    return task.getTaskType() == TaskType.USER_TASK || task.getTaskType() == TaskType.USER_TASK_WITH_EMAIL;
  }

  /**
   * Check if task has next approval task
   * 
   * @param taskList
   * @param userTaskIndex
   * @return check result
   */
  public boolean hasNextApprovalTask(List<TaskDef> taskList, int userTaskIndex) {
    List<TaskDef> subTaskList = taskList.subList(userTaskIndex + 1, taskList.size() - 1);
    for (TaskDef task : subTaskList) {
      if (task.getTaskType() == TaskType.USER_TASK || task.getTaskType() == TaskType.USER_TASK_WITH_EMAIL) {
        return false;
      }
      if (task.getTaskType() == TaskType.APPROVAL) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Check whether form definition step can be finished or not
   * 
   * @param definedTasks
   * @return check result
   */
  public boolean canFinishFormDefinition(List<TaskDef> definedTasks) {
    boolean isAnyCreateFormNotDefined
      = definedTasks.stream().filter(taskDef 
    		  -> (taskDef.getTaskType() !=  TaskType.EMAIL && taskDef.getTaskType() !=  TaskType.APPROVAL && taskDef.getDragAndDropController().isNotDefined()))
    		  .findFirst()
    		  .isPresent();

    boolean isAnyEmailEmpty
      = definedTasks.stream().filter(taskDef -> taskDef.getTaskType() ==  TaskType.EMAIL  && taskDef.getEmail().isEmpty()).findFirst().isPresent();

    return !isAnyCreateFormNotDefined && !isAnyEmailEmpty;
  }
}