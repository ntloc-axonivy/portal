[Ivy]
[>Created: Tue Jun 14 11:44:06 ICT 2016]
14E8ABD50C85F769 3.18 #module
>Proto >Proto Collection #zClass
Pe0 ProcessStartServiceIntegrator Big #zClass
Pe0 B #cInfo
Pe0 #process
Pe0 @TextInP .resExport .resExport #zField
Pe0 @TextInP .type .type #zField
Pe0 @TextInP .processKind .processKind #zField
Pe0 @AnnotationInP-0n ai ai #zField
Pe0 @TextInP .xml .xml #zField
Pe0 @TextInP .responsibility .responsibility #zField
Pe0 @ProcessException f0 '' #zField
Pe0 @EndSub f1 '' #zField
Pe0 @PushWFArc f2 '' #zField
Pe0 @EndSub f6 '' #zField
Pe0 @WSElement f7 '' #zField
Pe0 @WSElement f8 '' #zField
Pe0 @StartSub f9 '' #zField
Pe0 @Alternative f10 '' #zField
Pe0 @PushWFArc f12 '' #zField
Pe0 @PushWFArc f13 '' #zField
Pe0 @PushWFArc f15 '' #zField
Pe0 @GridStep f18 '' #zField
Pe0 @PushWFArc f11 '' #zField
Pe0 @PushWFArc f16 '' #zField
Pe0 @WSElement f3 '' #zField
Pe0 @PushWFArc f4 '' #zField
Pe0 @PushWFArc f5 '' #zField
Pe0 @PushWFArc f14 '' #zField
>Proto Pe0 Pe0 ProcessStartServiceIntegrator #zField
Pe0 f0 .resExport export #txt
Pe0 f0 actionDecl 'ch.ivy.add.portalkit.service.integrators.ProcessStartServiceIntegratorData out;
' #txt
Pe0 f0 actionTable 'out=in;
' #txt
Pe0 f0 actionCode ivy.log.fatal(exception); #txt
Pe0 f0 type ch.ivy.add.portalkit.service.integrators.ProcessStartServiceIntegratorData #txt
Pe0 f0 errorCode unspecified #txt
Pe0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>Exception Handler</name>
        <nameStyle>17,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Pe0 f0 691 51 26 26 14 0 #rect
Pe0 f0 @|ExceptionIcon #fIcon
Pe0 f1 type ch.ivy.add.portalkit.service.integrators.ProcessStartServiceIntegratorData #txt
Pe0 f1 691 179 26 26 14 0 #rect
Pe0 f1 @|EndSubIcon #fIcon
Pe0 f2 expr out #txt
Pe0 f2 704 77 704 179 #arcP
Pe0 f6 type ch.ivy.add.portalkit.service.integrators.ProcessStartServiceIntegratorData #txt
Pe0 f6 211 435 26 26 14 0 #rect
Pe0 f6 @|EndSubIcon #fIcon
Pe0 f7 type ch.ivy.add.portalkit.service.integrators.ProcessStartServiceIntegratorData #txt
Pe0 f7 actionDecl 'ch.ivy.add.portalkit.service.integrators.ProcessStartServiceIntegratorData out;
' #txt
Pe0 f7 actionTable 'out=in;
out.errors=wsResponse.findProcessesStartsByCriteriaResponse.result.errors;
out.processStarts=wsResponse.findProcessesStartsByCriteriaResponse.result.processStarts;
' #txt
Pe0 f7 cache '{/cache false /invalidation false /scope 0 /groupname ""/lifetime_group "0"/invalidation_time_group ""/identifier ""/lifetime_entry "0"/invalidation_time_entry ""}' #txt
Pe0 f7 timeout 10 #txt
Pe0 f7 beanConfig '"KEY_PASSWORD=<%\\=in.server.password%>
KEY_AXIS_PORTNAME=ProcessStartServicePort
KEY_WEBSERVICECONFIG_ID=1473A1D4EEBABA93
KEY_DOMAIN=<%\\=in.server.domain%>
KEY_USERNAME=<%\\=in.server.username%>
KEY_OPERATION=findProcessesStartsByCriteria
KEY_AUTHENTICATION_KIND=4
KEY_HOST=<%\\=in.server.host%>
KEY_USE_AUTHENTICATION=true
KEY_AXIS_CSL_PARAMETER_DATA=""arg0.findProcessesStartsByCriteria.language__@@__String__@@__in.language"",""arg0.findProcessesStartsByCriteria.processSearchCriteria__@@__ch.ivy.ws.addon.ProcessSearchCriteria__@@__in.processSearchCriteria"",""arg0.findProcessesStartsByCriteria.serverUrl__@@__String__@@__in.serverUrl"""' #txt
Pe0 f7 exceptionHandler 14E8ABD50C85F769-f0-buffer #txt
Pe0 f7 timeoutExceptionHandler 14E8ABD50C85F769-f0-buffer #txt
Pe0 f7 returningObjectList '[wsResponse]' #txt
Pe0 f7 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>NTLM</name>
        <nameStyle>4,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Pe0 f7 206 340 36 24 20 -2 #rect
Pe0 f7 @|WebServiceIcon #fIcon
Pe0 f8 type ch.ivy.add.portalkit.service.integrators.ProcessStartServiceIntegratorData #txt
Pe0 f8 actionDecl 'ch.ivy.add.portalkit.service.integrators.ProcessStartServiceIntegratorData out;
' #txt
Pe0 f8 actionTable 'out=in;
out.errors=wsResponse.findProcessesStartsByCriteriaResponse.result.errors;
out.processStarts=wsResponse.findProcessesStartsByCriteriaResponse.result.processStarts;
' #txt
Pe0 f8 cache '{/cache false /invalidation false /scope 0 /groupname ""/lifetime_group "0"/invalidation_time_group ""/identifier ""/lifetime_entry "0"/invalidation_time_entry ""}' #txt
Pe0 f8 timeout 10 #txt
Pe0 f8 beanConfig '"KEY_PASSWORD=
KEY_AXIS_PORTNAME=ProcessStartServicePort
KEY_WEBSERVICECONFIG_ID=1473A1D4EEBABA93
KEY_DOMAIN=
KEY_USERNAME=
KEY_OPERATION=findProcessesStartsByCriteria
KEY_AUTHENTICATION_KIND=0
KEY_HOST=
KEY_USE_AUTHENTICATION=false
KEY_AXIS_CSL_PARAMETER_DATA=""arg0.findProcessesStartsByCriteria.language__@@__String__@@__in.language"",""arg0.findProcessesStartsByCriteria.processSearchCriteria__@@__ch.ivy.ws.addon.ProcessSearchCriteria__@@__in.processSearchCriteria"",""arg0.findProcessesStartsByCriteria.serverUrl__@@__String__@@__in.serverUrl"""' #txt
Pe0 f8 exceptionHandler 14E8ABD50C85F769-f0-buffer #txt
Pe0 f8 timeoutExceptionHandler 14E8ABD50C85F769-f0-buffer #txt
Pe0 f8 returningObjectList '[wsResponse]' #txt
Pe0 f8 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>default settings</name>
        <nameStyle>16,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Pe0 f8 366 340 36 24 20 -2 #rect
Pe0 f8 @|WebServiceIcon #fIcon
Pe0 f9 inParamDecl '<java.lang.String endpoint,java.lang.String language,ch.ivy.ws.addon.ProcessSearchCriteria processSearchCriteria,ch.ivy.addon.portalkit.persistence.domain.Server server> param;' #txt
Pe0 f9 inParamTable 'out.endpoint=param.endpoint;
out.language=param.language;
out.processSearchCriteria=param.processSearchCriteria;
out.server=param.server;
' #txt
Pe0 f9 outParamDecl '<List<ch.ivy.ws.addon.IvyProcessStart> processStarts,List<ch.ivy.ws.addon.WsException> errors> result;
' #txt
Pe0 f9 outParamTable 'result.processStarts=in.processStarts;
result.errors=in.errors;
' #txt
Pe0 f9 actionDecl 'ch.ivy.add.portalkit.service.integrators.ProcessStartServiceIntegratorData out;
' #txt
Pe0 f9 callSignature findProcessStartsByCriteria(String,String,ch.ivy.ws.addon.ProcessSearchCriteria,ch.ivy.addon.portalkit.persistence.domain.Server) #txt
Pe0 f9 type ch.ivy.add.portalkit.service.integrators.ProcessStartServiceIntegratorData #txt
Pe0 f9 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>findProcessStartsByCriteria(String,ProcessSearchCriteria,Server)</name>
        <nameStyle>64,5,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Pe0 f9 211 51 26 26 14 0 #rect
Pe0 f9 @|StartSubIcon #fIcon
Pe0 f10 type ch.ivy.add.portalkit.service.integrators.ProcessStartServiceIntegratorData #txt
Pe0 f10 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>Authentication Type?</name>
        <nameStyle>20,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Pe0 f10 210 242 28 28 14 -22 #rect
Pe0 f10 @|AlternativeIcon #fIcon
Pe0 f12 expr in #txt
Pe0 f12 outCond 'ch.ivy.addon.portalkit.enums.WSAuthenticationType.NTLM == in.server.wsAuthenticationType' #txt
Pe0 f12 .xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name></name>
    </language>
</elementInfo>
' #txt
Pe0 f12 224 270 224 340 #arcP
Pe0 f13 expr out #txt
Pe0 f13 224 364 224 435 #arcP
Pe0 f15 expr out #txt
Pe0 f15 384 364 237 448 #arcP
Pe0 f15 1 384 448 #addKink
Pe0 f15 1 0.11094947753789884 0 0 #arcLabel
Pe0 f18 actionDecl 'ch.ivy.add.portalkit.service.integrators.ProcessStartServiceIntegratorData out;
' #txt
Pe0 f18 actionTable 'out=in;
' #txt
Pe0 f18 actionCode 'import ch.ivy.addon.portalkit.support.UrlDetector;
import org.apache.commons.lang.StringUtils;
UrlDetector detector = new UrlDetector();
String serverPath = (StringUtils.isEmpty(in.server.externalPath)) ? in.server.path : in.server.externalPath;
in.serverUrl = detector.getHost(serverPath);' #txt
Pe0 f18 type ch.ivy.add.portalkit.service.integrators.ProcessStartServiceIntegratorData #txt
Pe0 f18 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>get server Url from 
portal connector path</name>
        <nameStyle>42,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Pe0 f18 206 148 36 24 20 -19 #rect
Pe0 f18 @|StepIcon #fIcon
Pe0 f11 expr out #txt
Pe0 f11 224 172 224 242 #arcP
Pe0 f16 expr out #txt
Pe0 f16 224 77 224 148 #arcP
Pe0 f3 type ch.ivy.add.portalkit.service.integrators.ProcessStartServiceIntegratorData #txt
Pe0 f3 actionDecl 'ch.ivy.add.portalkit.service.integrators.ProcessStartServiceIntegratorData out;
' #txt
Pe0 f3 actionTable 'out=in;
out.errors=wsResponse.findProcessesStartsByCriteriaResponse.result.errors;
out.processStarts=wsResponse.findProcessesStartsByCriteriaResponse.result.processStarts;
' #txt
Pe0 f3 cache '{/cache false /invalidation false /scope 0 /groupname ""/lifetime_group "0"/invalidation_time_group ""/identifier ""/lifetime_entry "0"/invalidation_time_entry ""}' #txt
Pe0 f3 timeout 10 #txt
Pe0 f3 beanConfig '"KEY_PASSWORD=<%\\=in.server.password%>
KEY_AXIS_PORTNAME=ProcessStartServicePort
KEY_WEBSERVICECONFIG_ID=1473A1D4EEBABA93
KEY_DOMAIN=
KEY_USERNAME=<%\\=in.server.username%>
KEY_OPERATION=findProcessesStartsByCriteria
KEY_AUTHENTICATION_KIND=1
KEY_HOST=
KEY_USE_AUTHENTICATION=true
KEY_AXIS_CSL_PARAMETER_DATA=""arg0.findProcessesStartsByCriteria.language__@@__String__@@__in.language"",""arg0.findProcessesStartsByCriteria.processSearchCriteria__@@__ch.ivy.ws.addon.ProcessSearchCriteria__@@__in.processSearchCriteria"",""arg0.findProcessesStartsByCriteria.serverUrl__@@__String__@@__in.serverUrl"""' #txt
Pe0 f3 exceptionHandler 14E8ABD50C85F769-f0-buffer #txt
Pe0 f3 timeoutExceptionHandler 14E8ABD50C85F769-f0-buffer #txt
Pe0 f3 returningObjectList '[wsResponse]' #txt
Pe0 f3 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>HTTP Basic</name>
        <nameStyle>10,7
</nameStyle>
    </language>
</elementInfo>
' #txt
Pe0 f3 46 340 36 24 20 -2 #rect
Pe0 f3 @|WebServiceIcon #fIcon
Pe0 f4 expr in #txt
Pe0 f4 outCond 'ch.ivy.addon.portalkit.enums.WSAuthenticationType.HTTP_BASIC == in.server.wsAuthenticationType' #txt
Pe0 f4 210 256 64 340 #arcP
Pe0 f4 1 64 256 #addKink
Pe0 f4 0 0.7685782146822393 0 0 #arcLabel
Pe0 f5 expr out #txt
Pe0 f5 64 364 211 448 #arcP
Pe0 f5 1 64 448 #addKink
Pe0 f5 1 0.20679742620065877 0 0 #arcLabel
Pe0 f14 expr in #txt
Pe0 f14 238 256 384 340 #arcP
Pe0 f14 1 384 256 #addKink
Pe0 f14 0 0.7875410210393261 0 0 #arcLabel
>Proto Pe0 .type ch.ivy.add.portalkit.service.integrators.ProcessStartServiceIntegratorData #txt
>Proto Pe0 .processKind CALLABLE_SUB #txt
>Proto Pe0 0 0 32 24 18 0 #rect
>Proto Pe0 @|BIcon #fIcon
Pe0 f0 mainOut f2 tail #connect
Pe0 f2 head f1 mainIn #connect
Pe0 f10 out f12 tail #connect
Pe0 f12 head f7 mainIn #connect
Pe0 f7 mainOut f13 tail #connect
Pe0 f13 head f6 mainIn #connect
Pe0 f8 mainOut f15 tail #connect
Pe0 f15 head f6 mainIn #connect
Pe0 f18 mainOut f11 tail #connect
Pe0 f11 head f10 in #connect
Pe0 f9 mainOut f16 tail #connect
Pe0 f16 head f18 mainIn #connect
Pe0 f10 out f4 tail #connect
Pe0 f4 head f3 mainIn #connect
Pe0 f3 mainOut f5 tail #connect
Pe0 f5 head f6 mainIn #connect
Pe0 f10 out f14 tail #connect
Pe0 f14 head f8 mainIn #connect
