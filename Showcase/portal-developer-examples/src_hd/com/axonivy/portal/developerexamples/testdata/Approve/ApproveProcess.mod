[Ivy]
16E5DBC0E0749639 9.3.0 #module
>Proto >Proto Collection #zClass
As0 ApproveProcess Big #zClass
As0 RD #cInfo
As0 #process
As0 @TextInP .type .type #zField
As0 @TextInP .processKind .processKind #zField
As0 @AnnotationInP-0n ai ai #zField
As0 @MessageFlowInP-0n messageIn messageIn #zField
As0 @MessageFlowOutP-0n messageOut messageOut #zField
As0 @TextInP .xml .xml #zField
As0 @TextInP .responsibility .responsibility #zField
As0 @UdInit f0 '' #zField
As0 @UdProcessEnd f1 '' #zField
As0 @UdEvent f3 '' #zField
As0 @UdExitEnd f4 '' #zField
As0 @UdEvent f6 '' #zField
As0 @GridStep f7 '' #zField
As0 @PushWFArc f8 '' #zField
As0 @PushWFArc f5 '' #zField
As0 @PushWFArc f2 '' #zField
>Proto As0 As0 ApproveProcess #zField
As0 f0 guid 16B3FAF670F512D6 #txt
As0 f0 method start(com.axonivy.portal.developerexamples.ExampleIFrameData) #txt
As0 f0 inParameterDecl '<com.axonivy.portal.developerexamples.ExampleIFrameData investmentRequest> param;' #txt
As0 f0 inParameterMapAction 'out.investmentRequest=param.investmentRequest;
' #txt
As0 f0 outParameterDecl '<com.axonivy.portal.developerexamples.ExampleIFrameData investmentRequest> result;' #txt
As0 f0 outParameterMapAction 'result.investmentRequest=in.investmentRequest;
' #txt
As0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start(ExampleIFrameData)</name>
    </language>
</elementInfo>
' #txt
As0 f0 83 51 26 26 -16 15 #rect
As0 f1 211 51 26 26 0 12 #rect
As0 f3 guid 16B3FAF673CD7CD2 #txt
As0 f3 actionTable 'out=in;
out.investmentRequest=in.investmentRequest;
' #txt
As0 f3 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>close</name>
    </language>
</elementInfo>
' #txt
As0 f3 83 147 26 26 -15 12 #rect
As0 f4 211 147 26 26 0 12 #rect
As0 f6 guid 1734C8917D863C2E #txt
As0 f6 actionTable 'out=in;
' #txt
As0 f6 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>home</name>
    </language>
</elementInfo>
' #txt
As0 f6 83 243 26 26 -14 15 #rect
As0 f7 actionTable 'out=in;
' #txt
As0 f7 actionCode 'import javax.faces.context.FacesContext;

FacesContext.getCurrentInstance().getExternalContext().redirect(ivy.html.applicationHomeRef());' #txt
As0 f7 168 234 112 44 0 -8 #rect
As0 f8 109 256 168 256 #arcP
As0 f5 109 160 211 160 #arcP
As0 f2 109 64 211 64 #arcP
>Proto As0 .type com.axonivy.portal.developerexamples.testdata.Approve.ApproveData #txt
>Proto As0 .processKind HTML_DIALOG #txt
>Proto As0 -8 -8 16 16 16 26 #rect
As0 f6 mainOut f8 tail #connect
As0 f8 head f7 mainIn #connect
As0 f3 mainOut f5 tail #connect
As0 f5 head f4 mainIn #connect
As0 f0 mainOut f2 tail #connect
As0 f2 head f1 mainIn #connect