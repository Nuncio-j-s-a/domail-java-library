package sk.dominanz.domail;

import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import sk.dominanz.domail.input.rest.api.CommunicationApi;
import sk.dominanz.domail.input.rest.api.EmailApi;
import sk.dominanz.domail.input.rest.model.CommunicationDetail;
import sk.dominanz.domail.input.rest.model.CommunicationId;
import sk.dominanz.domail.input.rest.model.CommunicationTemplateId;
import sk.dominanz.domail.input.rest.model.EmailSendAdvancedRequest;
import sk.dominanz.domail.input.rest.model.EmailSendRequest;
import sk.dominanz.domail.input.rest.model.EmailSendWithTemplateAdvancedRequest;
import sk.dominanz.domail.input.rest.model.EmailSendWithTemplateRequest;
import sk.dominanz.domail.input.rest.util.ApiClient;

public class DomailClientRest {

	private CommunicationApi communicationApi;
	private EmailApi emailApi;
	
	private DomailClientRest() {}
	
	public DomailClientRest(String url){
		this();
		RestTemplate restTemplate = new RestTemplate();
		for(HttpMessageConverter<?> converter : restTemplate.getMessageConverters()) {
        	if(converter instanceof MappingJackson2HttpMessageConverter) {
        		ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                ((MappingJackson2HttpMessageConverter)converter).setObjectMapper(mapper);
        		break;
        	}
        }
		ApiClient c = new ApiClient(restTemplate);
		c.setBasePath(url);
		emailApi = new EmailApi(c);
		communicationApi = new CommunicationApi(c);
	}
	
	public CommunicationId sendEmail(EmailSendRequest esr) {
		return emailApi.emailSendSimple(esr);
	}
	
	public CommunicationId sendEmailAdvanced(EmailSendAdvancedRequest esar) {
		return emailApi.emailSendAdvanced(esar);
	}
	
	public CommunicationTemplateId sendEmailTemplate(EmailSendWithTemplateRequest eswtr) {
		return emailApi.emailSendWithTemplate(eswtr);
	}
	
	public CommunicationTemplateId sendEmailTemplateAdvanced(EmailSendWithTemplateAdvancedRequest eswtar) {
		return emailApi.emailSendWithTemplateAdvanced(eswtar);
	}
	
	public CommunicationDetail getCommunicationInfo(Long id) {
		return communicationApi.communicationIdDetailGet(id);
	}
	
	public List<CommunicationDetail> getCommunicationInfo(String extId) {
		return communicationApi.communicationExtidExtIdDetailGet(extId);
	}
	
	public sk.dominanz.domail.input.rest.model.CommunicationStateType getCommunicationState(Long id) {
		return communicationApi.communicationIdStateGet(id);
	}
	
	public List<sk.dominanz.domail.input.rest.model.CommunicationStateType> getCommunicationState(String extId) {
		return communicationApi.communicationExtidExtIdStateGet(extId);
	}
}
