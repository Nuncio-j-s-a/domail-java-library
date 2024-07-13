package sk.dominanz.domail;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import sk.dominanz.domail.soap.CommunicationGetRequestType;
import sk.dominanz.domail.soap.CommunicationGetResponseType;
import sk.dominanz.domail.soap.CommunicationGetStateRequestType;
import sk.dominanz.domail.soap.CommunicationGetStateResponseType;
import sk.dominanz.domail.soap.CommunicationResponseType;
import sk.dominanz.domail.soap.DomailCommunication;
import sk.dominanz.domail.soap.DominanzDomailCommunication;
import sk.dominanz.domail.soap.EmailSendAdvRequestType;
import sk.dominanz.domail.soap.EmailSendRequestType;
import sk.dominanz.domail.soap.EmailSendWithTemplateAdvRequestType;
import sk.dominanz.domail.soap.EmailSendWithTemplateRequestType;
import sk.dominanz.domail.soap.EmailSendWithTemplateResponseType;

public class DomailClientSoap {

	private DomailCommunication port;
	
	private DomailClientSoap() {}
	
	public DomailClientSoap(String url) throws MalformedURLException{
		this();
		System.setProperty("javax.xml.soap.SAAJMetaFactory", "com.sun.xml.messaging.saaj.soap.SAAJMetaFactoryImpl");
		DominanzDomailCommunication service = new DominanzDomailCommunication();
		service.getPorts();
		//port = service.getHTTPPort();
		port = service.getHTTPSPort();
		BindingProvider bindingProvider = (BindingProvider) port;
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
	}
	
	public CommunicationResponseType sendEmail(EmailSendRequestType e) {
		return port.emailSend(e);
	}
	
	public CommunicationResponseType sendEmailAdvanced(EmailSendAdvRequestType e) {
		return port.emailSendAdv(e);
	}
	
	public EmailSendWithTemplateResponseType sendEmailTemplate(EmailSendWithTemplateRequestType e) {
		return port.emailSendWithTemplate(e);
	}
	
	public EmailSendWithTemplateResponseType sendEmailTemplateAdvanced(EmailSendWithTemplateAdvRequestType e) {
		return port.emailSendWithTemplateAdv(e);
	}
	
	public CommunicationGetResponseType getCommunicationInfo(CommunicationGetRequestType r) {
		return port.communicationGet(r);
	}
	
	public CommunicationGetStateResponseType getCommunicationState(CommunicationGetStateRequestType r) {
		return port.communicationGetState(r);
	}
 
}
