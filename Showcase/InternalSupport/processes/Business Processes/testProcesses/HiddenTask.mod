[Ivy]
[>Created: Mon May 15 11:25:08 ICT 2017]
15C0A4B1BA58DEA3 3.20 #module
>Proto >Proto Collection #zClass
Hk0 HiddenTask Big #zClass
Hk0 B #cInfo
Hk0 #process
Hk0 @TextInP .resExport .resExport #zField
Hk0 @TextInP .type .type #zField
Hk0 @TextInP .processKind .processKind #zField
Hk0 @AnnotationInP-0n ai ai #zField
Hk0 @MessageFlowInP-0n messageIn messageIn #zField
Hk0 @MessageFlowOutP-0n messageOut messageOut #zField
Hk0 @TextInP .xml .xml #zField
Hk0 @TextInP .responsibility .responsibility #zField
Hk0 @StartRequest f0 '' #zField
Hk0 @EndTask f1 '' #zField
Hk0 @TaskSwitchSimple f3 '' #zField
Hk0 @TkArc f4 '' #zField
Hk0 @GridStep f5 '' #zField
Hk0 @PushWFArc f6 '' #zField
Hk0 @PushWFArc f2 '' #zField
>Proto Hk0 Hk0 HiddenTask #zField
Hk0 f0 outLink start.ivp #txt
Hk0 f0 type internaltest.Data #txt
Hk0 f0 inParamDecl '<> param;' #txt
Hk0 f0 actionDecl 'internaltest.Data out;
' #txt
Hk0 f0 guid 15C0A4B1BBCB4944 #txt
Hk0 f0 requestEnabled true #txt
Hk0 f0 triggerEnabled false #txt
Hk0 f0 callSignature start() #txt
Hk0 f0 persist false #txt
Hk0 f0 startName 'Hidden Task' #txt
Hk0 f0 startDescription 'Task, which has the HIDE additional property, won''t be displayed in Portal' #txt
Hk0 f0 taskData 'TaskTriggered.ROL=Everybody
TaskTriggered.EXTYPE=0
TaskTriggered.EXPRI=2
TaskTriggered.TYPE=0
TaskTriggered.PRI=2
TaskTriggered.EXROL=Everybody' #txt
Hk0 f0 caseData businessCase.attach=true #txt
Hk0 f0 showInStartList 1 #txt
Hk0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start.ivp</name>
        <nameStyle>9,5,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Hk0 f0 @C|.responsibility Everybody #txt
Hk0 f0 81 49 30 30 -21 17 #rect
Hk0 f0 @|StartRequestIcon #fIcon
Hk0 f1 type internaltest.Data #txt
Hk0 f1 465 49 30 30 0 15 #rect
Hk0 f1 @|EndIcon #fIcon
Hk0 f3 actionDecl 'internaltest.Data out;
' #txt
Hk0 f3 actionTable 'out=in1;
' #txt
Hk0 f3 outTypes "internaltest.Data" #txt
Hk0 f3 outLinks "TaskA.ivp" #txt
Hk0 f3 caseData 'case.name=Case contains hidden task' #txt
Hk0 f3 taskData 'TaskA.DESC=Task, which has the HIDE additional property, won''t be displayed in Portal
TaskA.EXPRI=2
TaskA.EXROL=Everybody
TaskA.EXTYPE=0
TaskA.NAM=Hidden Task
TaskA.PRI=2
TaskA.ROL=Everybody
TaskA.SKIP_TASK_LIST=true
TaskA.TYPE=0' #txt
Hk0 f3 type internaltest.Data #txt
Hk0 f3 template "" #txt
Hk0 f3 177 49 30 30 0 16 #rect
Hk0 f3 @|TaskSwitchSimpleIcon #fIcon
Hk0 f4 expr out #txt
Hk0 f4 type internaltest.Data #txt
Hk0 f4 var in1 #txt
Hk0 f4 111 64 177 64 #arcP
Hk0 f5 actionDecl 'internaltest.Data out;
' #txt
Hk0 f5 actionTable 'out=in;
' #txt
Hk0 f5 actionCode 'import ch.ivy.addon.portalkit.util.TaskUtils;
//TaskUtils.setHidePropertyToHideInPortal(ivy.task);' #txt
Hk0 f5 type internaltest.Data #txt
Hk0 f5 264 42 112 44 0 -8 #rect
Hk0 f5 @|StepIcon #fIcon
Hk0 f6 expr data #txt
Hk0 f6 outCond ivp=="TaskA.ivp" #txt
Hk0 f6 207 64 264 64 #arcP
Hk0 f2 expr out #txt
Hk0 f2 376 64 465 64 #arcP
>Proto Hk0 .type internaltest.Data #txt
>Proto Hk0 .processKind NORMAL #txt
>Proto Hk0 0 0 32 24 18 0 #rect
>Proto Hk0 @|BIcon #fIcon
Hk0 f0 mainOut f4 tail #connect
Hk0 f4 head f3 in #connect
Hk0 f3 out f6 tail #connect
Hk0 f6 head f5 mainIn #connect
Hk0 f5 mainOut f2 tail #connect
Hk0 f2 head f1 mainIn #connect
