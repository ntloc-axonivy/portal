[Ivy]
17C348931C4F08FD 9.3.0 #module
>Proto >Proto Collection #zClass
Tt0 DataCreationESRestClient Big #zClass
Tt0 B #cInfo
Tt0 #process
Tt0 @AnnotationInP-0n ai ai #zField
Tt0 @TextInP .type .type #zField
Tt0 @TextInP .processKind .processKind #zField
Tt0 @TextInP .xml .xml #zField
Tt0 @TextInP .responsibility .responsibility #zField
Tt0 @StartRequest f0 '' #zField
Tt0 @EndTask f1 '' #zField
Tt0 @GridStep f3 '' #zField
Tt0 @PushWFArc f4 '' #zField
Tt0 @PushWFArc f2 '' #zField
Tt0 @RestClientCall f15 '' #zField
Tt0 @StartRequest f16 '' #zField
Tt0 @PushWFArc f17 '' #zField
Tt0 @EndTask f18 '' #zField
Tt0 @PushWFArc f19 '' #zField
Tt0 @StartRequest f5 '' #zField
Tt0 @EndTask f6 '' #zField
Tt0 @TaskSwitchSimple f8 '' #zField
Tt0 @TkArc f9 '' #zField
Tt0 @GridStep f10 '' #zField
Tt0 @PushWFArc f11 '' #zField
Tt0 @PushWFArc f7 '' #zField
Tt0 @TaskSwitchSimple f12 '' #zField
Tt0 @GridStep f13 '' #zField
Tt0 @EndTask f14 '' #zField
Tt0 @StartRequest f20 '' #zField
Tt0 @TkArc f21 '' #zField
Tt0 @PushWFArc f22 '' #zField
Tt0 @PushWFArc f23 '' #zField
Tt0 @TaskSwitchSimple f24 '' #zField
Tt0 @GridStep f25 '' #zField
Tt0 @EndTask f26 '' #zField
Tt0 @StartRequest f27 '' #zField
Tt0 @PushWFArc f28 '' #zField
Tt0 @TkArc f29 '' #zField
Tt0 @PushWFArc f30 '' #zField
Tt0 @TaskSwitchSimple f31 '' #zField
Tt0 @GridStep f32 '' #zField
Tt0 @EndTask f33 '' #zField
Tt0 @StartRequest f34 '' #zField
Tt0 @PushWFArc f35 '' #zField
Tt0 @PushWFArc f36 '' #zField
Tt0 @TkArc f37 '' #zField
Tt0 @StartRequest f38 '' #zField
Tt0 @TaskSwitchSimple f39 '' #zField
Tt0 @EndTask f40 '' #zField
Tt0 @GridStep f41 '' #zField
Tt0 @TkArc f42 '' #zField
Tt0 @PushWFArc f43 '' #zField
Tt0 @PushWFArc f44 '' #zField
>Proto Tt0 Tt0 DataCreationESRestClient #zField
Tt0 f0 outLink start.ivp #txt
Tt0 f0 inParamDecl '<> param;' #txt
Tt0 f0 requestEnabled true #txt
Tt0 f0 triggerEnabled false #txt
Tt0 f0 callSignature start() #txt
Tt0 f0 caseData businessCase.attach=true #txt
Tt0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start.ivp</name>
    </language>
</elementInfo>
' #txt
Tt0 f0 @C|.responsibility Everybody #txt
Tt0 f0 81 49 30 30 -21 17 #rect
Tt0 f1 337 49 30 30 0 15 #rect
Tt0 f3 actionTable 'out=in;
' #txt
Tt0 f3 actionCode 'import ch.ivy.addon.portalkit.statistics.es.service.ESRestHighClientService;

ESRestHighClientService.test();' #txt
Tt0 f3 security system #txt
Tt0 f3 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>call rest</name>
    </language>
</elementInfo>
' #txt
Tt0 f3 168 42 112 44 -21 -8 #rect
Tt0 f4 111 64 168 64 #arcP
Tt0 f2 280 64 337 64 #arcP
Tt0 f15 288 178 112 44 0 -8 #rect
Tt0 f16 outLink start4.ivp #txt
Tt0 f16 inParamDecl '<> param;' #txt
Tt0 f16 requestEnabled true #txt
Tt0 f16 triggerEnabled false #txt
Tt0 f16 callSignature start4() #txt
Tt0 f16 caseData businessCase.attach=true #txt
Tt0 f16 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start4.ivp</name>
    </language>
</elementInfo>
' #txt
Tt0 f16 @C|.responsibility Everybody #txt
Tt0 f16 73 185 30 30 -24 17 #rect
Tt0 f17 103 200 288 200 #arcP
Tt0 f18 489 185 30 30 0 15 #rect
Tt0 f19 400 200 489 200 #arcP
Tt0 f5 outLink start5.ivp #txt
Tt0 f5 inParamDecl '<> param;' #txt
Tt0 f5 requestEnabled true #txt
Tt0 f5 triggerEnabled false #txt
Tt0 f5 callSignature start5() #txt
Tt0 f5 caseData businessCase.attach=true #txt
Tt0 f5 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start5.ivp</name>
    </language>
</elementInfo>
' #txt
Tt0 f5 @C|.responsibility Everybody #txt
Tt0 f5 81 401 30 30 -24 17 #rect
Tt0 f6 329 401 30 30 0 15 #rect
Tt0 f8 actionTable 'out=in1;
' #txt
Tt0 f8 taskData TaskA.SKIP_TASK_LIST=true #txt
Tt0 f8 137 401 30 30 0 16 #rect
Tt0 f9 111 416 137 416 #arcP
Tt0 f10 actionTable 'out=in;
' #txt
Tt0 f10 actionCode 'ivy.case.categoryPath = "Test 1";
ivy.case.customFields().stringField("Region").set("HCMC");
ivy.task.customFields().stringField("PurchaseType").set("House");' #txt
Tt0 f10 security system #txt
Tt0 f10 192 394 112 44 0 -8 #rect
Tt0 f11 167 416 192 416 #arcP
Tt0 f7 304 416 329 416 #arcP
Tt0 f12 actionTable 'out=in1;
' #txt
Tt0 f12 taskData TaskA.SKIP_TASK_LIST=true #txt
Tt0 f12 489 393 30 30 0 16 #rect
Tt0 f13 actionTable 'out=in;
' #txt
Tt0 f13 actionCode 'ivy.case.categoryPath = "Test 1";
ivy.case.customFields().stringField("Region").set("USA");
ivy.task.customFields().stringField("PurchaseType").set("Car");' #txt
Tt0 f13 security system #txt
Tt0 f13 544 386 112 44 0 -8 #rect
Tt0 f14 689 393 30 30 0 15 #rect
Tt0 f20 outLink start6.ivp #txt
Tt0 f20 inParamDecl '<> param;' #txt
Tt0 f20 requestEnabled true #txt
Tt0 f20 triggerEnabled false #txt
Tt0 f20 callSignature start6() #txt
Tt0 f20 caseData businessCase.attach=true #txt
Tt0 f20 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start6.ivp</name>
    </language>
</elementInfo>
' #txt
Tt0 f20 @C|.responsibility Everybody #txt
Tt0 f20 425 393 30 30 -24 17 #rect
Tt0 f21 455 408 489 408 #arcP
Tt0 f22 656 408 689 408 #arcP
Tt0 f23 519 408 544 408 #arcP
Tt0 f24 actionTable 'out=in1;
' #txt
Tt0 f24 taskData TaskA.SKIP_TASK_LIST=true #txt
Tt0 f24 145 537 30 30 0 16 #rect
Tt0 f25 actionTable 'out=in;
' #txt
Tt0 f25 actionCode 'ivy.case.categoryPath = "New Test 2";
ivy.case.customFields().stringField("Region").set("Europe");
ivy.task.customFields().stringField("PurchaseType").set("Foods");' #txt
Tt0 f25 security system #txt
Tt0 f25 200 530 112 44 0 -8 #rect
Tt0 f26 337 537 30 30 0 15 #rect
Tt0 f27 outLink start7.ivp #txt
Tt0 f27 inParamDecl '<> param;' #txt
Tt0 f27 requestEnabled true #txt
Tt0 f27 triggerEnabled false #txt
Tt0 f27 callSignature start7() #txt
Tt0 f27 caseData businessCase.attach=true #txt
Tt0 f27 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start7.ivp</name>
    </language>
</elementInfo>
' #txt
Tt0 f27 @C|.responsibility Everybody #txt
Tt0 f27 89 537 30 30 -24 17 #rect
Tt0 f28 175 552 200 552 #arcP
Tt0 f29 119 552 145 552 #arcP
Tt0 f30 312 552 337 552 #arcP
Tt0 f31 actionTable 'out=in1;
' #txt
Tt0 f31 taskData TaskA.SKIP_TASK_LIST=true #txt
Tt0 f31 489 529 30 30 0 16 #rect
Tt0 f32 actionTable 'out=in;
' #txt
Tt0 f32 actionCode 'ivy.case.categoryPath = "New Test 2";
ivy.case.customFields().stringField("Region").set("Africa");
ivy.task.customFields().stringField("PurchaseType").set("Cellphone");' #txt
Tt0 f32 security system #txt
Tt0 f32 544 522 112 44 0 -8 #rect
Tt0 f33 689 529 30 30 0 15 #rect
Tt0 f34 outLink start8.ivp #txt
Tt0 f34 inParamDecl '<> param;' #txt
Tt0 f34 requestEnabled true #txt
Tt0 f34 triggerEnabled false #txt
Tt0 f34 callSignature start8() #txt
Tt0 f34 caseData businessCase.attach=true #txt
Tt0 f34 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start8.ivp</name>
    </language>
</elementInfo>
' #txt
Tt0 f34 @C|.responsibility Everybody #txt
Tt0 f34 425 529 30 30 -24 17 #rect
Tt0 f35 519 544 544 544 #arcP
Tt0 f36 656 544 689 544 #arcP
Tt0 f37 455 544 489 544 #arcP
Tt0 f38 outLink start9.ivp #txt
Tt0 f38 inParamDecl '<> param;' #txt
Tt0 f38 requestEnabled true #txt
Tt0 f38 triggerEnabled true #txt
Tt0 f38 callSignature start9() #txt
Tt0 f38 taskData 'TaskTriggered.ROL=SYSTEM
TaskTriggered.TYPE=0' #txt
Tt0 f38 caseData businessCase.attach=true #txt
Tt0 f38 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start9.ivp</name>
    </language>
</elementInfo>
' #txt
Tt0 f38 @C|.responsibility Everybody #txt
Tt0 f38 97 665 30 30 -24 17 #rect
Tt0 f39 actionTable 'out=in1;
' #txt
Tt0 f39 taskData 'TaskA.ROL=SYSTEM
TaskA.SKIP_TASK_LIST=true
TaskA.TYPE=0' #txt
Tt0 f39 153 665 30 30 0 16 #rect
Tt0 f40 345 665 30 30 0 15 #rect
Tt0 f41 actionTable 'out=in;
' #txt
Tt0 f41 actionCode 'ivy.case.categoryPath = "New Test 2";
ivy.case.customFields().stringField("Region").set("Europe");
ivy.task.customFields().stringField("PurchaseType").set("Clothes");' #txt
Tt0 f41 security system #txt
Tt0 f41 208 658 112 44 0 -8 #rect
Tt0 f42 127 680 153 680 #arcP
Tt0 f43 320 680 345 680 #arcP
Tt0 f44 183 680 208 680 #arcP
>Proto Tt0 .type portalKit_test.DataCreationData #txt
>Proto Tt0 .processKind NORMAL #txt
>Proto Tt0 0 0 32 24 18 0 #rect
>Proto Tt0 @|BIcon #fIcon
Tt0 f0 mainOut f4 tail #connect
Tt0 f4 head f3 mainIn #connect
Tt0 f3 mainOut f2 tail #connect
Tt0 f2 head f1 mainIn #connect
Tt0 f16 mainOut f17 tail #connect
Tt0 f17 head f15 mainIn #connect
Tt0 f15 mainOut f19 tail #connect
Tt0 f19 head f18 mainIn #connect
Tt0 f5 mainOut f9 tail #connect
Tt0 f9 head f8 in #connect
Tt0 f8 out f11 tail #connect
Tt0 f11 head f10 mainIn #connect
Tt0 f10 mainOut f7 tail #connect
Tt0 f7 head f6 mainIn #connect
Tt0 f20 mainOut f21 tail #connect
Tt0 f21 head f12 in #connect
Tt0 f12 out f23 tail #connect
Tt0 f23 head f13 mainIn #connect
Tt0 f13 mainOut f22 tail #connect
Tt0 f22 head f14 mainIn #connect
Tt0 f27 mainOut f29 tail #connect
Tt0 f29 head f24 in #connect
Tt0 f24 out f28 tail #connect
Tt0 f28 head f25 mainIn #connect
Tt0 f25 mainOut f30 tail #connect
Tt0 f30 head f26 mainIn #connect
Tt0 f34 mainOut f37 tail #connect
Tt0 f37 head f31 in #connect
Tt0 f31 out f35 tail #connect
Tt0 f35 head f32 mainIn #connect
Tt0 f32 mainOut f36 tail #connect
Tt0 f36 head f33 mainIn #connect
Tt0 f38 mainOut f42 tail #connect
Tt0 f42 head f39 in #connect
Tt0 f39 out f44 tail #connect
Tt0 f44 head f41 mainIn #connect
Tt0 f41 mainOut f43 tail #connect
Tt0 f43 head f40 mainIn #connect
