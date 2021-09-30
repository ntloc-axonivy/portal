[Ivy]
17C15CAD0D7535C8 9.3.0 #module
>Proto >Proto Collection #zClass
ET0 ElasticsearchREST Big #zClass
ET0 B #cInfo
ET0 #process
ET0 @AnnotationInP-0n ai ai #zField
ET0 @TextInP .type .type #zField
ET0 @TextInP .processKind .processKind #zField
ET0 @TextInP .xml .xml #zField
ET0 @TextInP .responsibility .responsibility #zField
ET0 @StartRequest f0 '' #zField
ET0 @EndTask f1 '' #zField
ET0 @RestClientCall f3 '' #zField
ET0 @PushWFArc f4 '' #zField
ET0 @PushWFArc f2 '' #zField
ET0 @StartRequest f5 '' #zField
ET0 @EndTask f6 '' #zField
ET0 @RestClientCall f8 '' #zField
ET0 @PushWFArc f9 '' #zField
ET0 @PushWFArc f7 '' #zField
>Proto ET0 ET0 ElasticsearchREST #zField
ET0 f0 outLink start.ivp #txt
ET0 f0 inParamDecl '<> param;' #txt
ET0 f0 requestEnabled true #txt
ET0 f0 triggerEnabled false #txt
ET0 f0 callSignature start() #txt
ET0 f0 caseData businessCase.attach=true #txt
ET0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start.ivp</name>
    </language>
</elementInfo>
' #txt
ET0 f0 @C|.responsibility Everybody #txt
ET0 f0 81 49 30 30 -21 17 #rect
ET0 f1 337 49 30 30 0 15 #rect
ET0 f3 clientId 1f4b4dd6-c518-42f9-8516-4fcb6dd4c22e #txt
ET0 f3 path /account #txt
ET0 f3 resultType com.elastic.cloud.api.api.v1.client.AccountResponse #txt
ET0 f3 clientErrorCode ivy:error:rest:client #txt
ET0 f3 statusErrorCode ivy:error:rest:client #txt
ET0 f3 168 42 112 44 0 -8 #rect
ET0 f4 111 64 168 64 #arcP
ET0 f2 280 64 337 64 #arcP
ET0 f5 outLink start2.ivp #txt
ET0 f5 inParamDecl '<> param;' #txt
ET0 f5 requestEnabled true #txt
ET0 f5 triggerEnabled false #txt
ET0 f5 callSignature start2() #txt
ET0 f5 caseData businessCase.attach=true #txt
ET0 f5 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start2.ivp</name>
    </language>
</elementInfo>
' #txt
ET0 f5 @C|.responsibility Everybody #txt
ET0 f5 81 273 30 30 -24 17 #rect
ET0 f6 401 273 30 30 0 15 #rect
ET0 f8 208 266 112 44 0 -8 #rect
ET0 f9 111 288 208 288 #arcP
ET0 f7 320 288 401 288 #arcP
>Proto ET0 .type portalkit.ElasticsearchRESTData #txt
>Proto ET0 .processKind NORMAL #txt
>Proto ET0 0 0 32 24 18 0 #rect
>Proto ET0 @|BIcon #fIcon
ET0 f0 mainOut f4 tail #connect
ET0 f4 head f3 mainIn #connect
ET0 f3 mainOut f2 tail #connect
ET0 f2 head f1 mainIn #connect
ET0 f5 mainOut f9 tail #connect
ET0 f9 head f8 mainIn #connect
ET0 f8 mainOut f7 tail #connect
ET0 f7 head f6 mainIn #connect
