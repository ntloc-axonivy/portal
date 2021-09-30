[Ivy]
17C3487F62373FD0 9.3.0 #module
>Proto >Proto Collection #zClass
Ss0 StatisticStart Big #zClass
Ss0 B #cInfo
Ss0 #process
Ss0 @AnnotationInP-0n ai ai #zField
Ss0 @TextInP .type .type #zField
Ss0 @TextInP .processKind .processKind #zField
Ss0 @TextInP .xml .xml #zField
Ss0 @TextInP .responsibility .responsibility #zField
Ss0 @StartRequest f0 '' #zField
Ss0 @EndTask f1 '' #zField
Ss0 @UserDialog f3 '' #zField
Ss0 @PushWFArc f4 '' #zField
Ss0 @PushWFArc f2 '' #zField
>Proto Ss0 Ss0 StatisticStart #zField
Ss0 f0 outLink startShowChartDemo.ivp #txt
Ss0 f0 inParamDecl '<> param;' #txt
Ss0 f0 requestEnabled true #txt
Ss0 f0 triggerEnabled false #txt
Ss0 f0 callSignature startShowChartDemo() #txt
Ss0 f0 startName 'Start Show Chart ES Demo' #txt
Ss0 f0 taskData TaskTriggered.customFields.STRING.embedInFrame="false" #txt
Ss0 f0 caseData 'businessCase.attach=true
customFields.STRING.embedInFrame="false"' #txt
Ss0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>startShowChartDemo.ivp</name>
    </language>
</elementInfo>
' #txt
Ss0 f0 @C|.responsibility Everybody #txt
Ss0 f0 81 49 30 30 -21 17 #rect
Ss0 f1 337 49 30 30 0 15 #rect
Ss0 f3 dialogId ch.ivy.addon.portal.generic.dashboard.ChartsDemo #txt
Ss0 f3 startMethod start() #txt
Ss0 f3 requestActionDecl '<> param;' #txt
Ss0 f3 responseMappingAction 'out=in;
' #txt
Ss0 f3 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>ChartsDemo</name>
    </language>
</elementInfo>
' #txt
Ss0 f3 168 42 112 44 -35 -8 #rect
Ss0 f4 111 64 168 64 #arcP
Ss0 f2 280 64 337 64 #arcP
>Proto Ss0 .type ch.ivy.addon.portal.generic.Data #txt
>Proto Ss0 .processKind NORMAL #txt
>Proto Ss0 0 0 32 24 18 0 #rect
>Proto Ss0 @|BIcon #fIcon
Ss0 f0 mainOut f4 tail #connect
Ss0 f4 head f3 mainIn #connect
Ss0 f3 mainOut f2 tail #connect
Ss0 f2 head f1 mainIn #connect
