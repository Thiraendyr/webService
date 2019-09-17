package com.dtalavera.ejerciciosoa.crudaws.services;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.dtalavera.ejerciciosoa.crudaws.config.Auth;
import com.dtalavera.ejerciciosoa.crudaws.entity.Contact;
import com.dtalavera.ejerciciosoa.crudaws.methods.GetMethods;
import com.dtalavera.ejerciciosoa.crudaws.methods.ReplaceChars;
import com.dtalavera.ejerciciosoa.crudaws.models.OSC.ContactOSC;
import com.dtalavera.ejerciciosoa.crudaws.models.OSC.LeadOSC;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@org.springframework.stereotype.Service
public class ServiceOS {
	
	public ServiceOS() {
		// TODO Auto-generated constructor stub
	}

//////////////////////////////////////Oracle Sales Cloud
	//Busca un Contact por email
	public Contact getContact(String email) {
		try {
			return GetMethods.getOSContactByEmail(ReplaceChars.transFormarLetras(email));
			
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	//Elimina un Contact por email
	public void deleteOSContact(String email) {
		try {
			Contact contacto = GetMethods.getOSContactByEmail(email);
			if(contacto.getId() != 0L) {
			
				CloseableHttpClient httpclient = HttpClientBuilder.create().build();
				HttpDelete httpDelete  = Auth.setDeleteContactHeaders("os", contacto.getId());
				
				httpclient.execute(httpDelete);
				
				deleteOSLeadsByEmail(email);
	
				httpclient.close();
			
			}
		
		} catch(IOException | JSONException e) {
			e.printStackTrace();
		}
	}
	
	//Elimina un lead asociado al Email de un contacto
	private static boolean deleteOSLeadsByEmail(String email) {
		try {
			LeadOSC lead = GetMethods.getOSLeadByPrimaryContactEmailAddress(email);
			CloseableHttpClient client = HttpClientBuilder.create().build();
			//Asigno el leadId a ContactPartyNumber de leadOSC
			HttpDelete request = Auth.setDeleteContactHeaders("osLead", lead.getContactPartyNumber());
		    
			client.execute(request);
			
		    client.close();
			
			return true;
			
		} catch(IOException | JSONException e) {
			return false;
		}
	}
	
	//Hace post del contacto desde el ContactOSC recibido por json
	private Contact createOSContact(String json, String email) {
		try {
			Contact contacto = GetMethods.getOSContactByEmail(email);
			if(contacto.getId() == 0L){
			
				CloseableHttpClient client = HttpClients.createDefault();
				HttpPost httpPost  = Auth.setPostContactHeaders("os", json);
				
				HttpResponse response = client.execute(httpPost);
				
				if(response.getStatusLine().getStatusCode() == 201) {
				    client.close();
					return GetMethods.getRNContactByEmail(email);
				}
			    
			}
		} catch(IOException | JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//Realiza un post desde el json del LeadOSC
	private LeadOSC createOSLead(String json, String email) {
		try {
			LeadOSC lead = GetMethods.getOSLeadByPrimaryContactEmailAddress(email);
			if(lead.getContactPartyNumber() == 0L){
			
				CloseableHttpClient client = HttpClients.createDefault();
				HttpPost httpPost  = Auth.setPostContactHeaders("osLead", json);
				
				HttpResponse response = client.execute(httpPost);
			    
				if(response.getStatusLine().getStatusCode() == 201) {
					client.close();
					return GetMethods.getOSLeadByPrimaryContactEmailAddress(email);
				}
			}
		} catch(IOException | JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//Serializa un json a ContactOSC y crea el contacto
	public Contact serializarObjectoContact(String jsonReceived){
		try {
			JSONObject jsonObject = new JSONObject(jsonReceived);
			
			ContactOSC contactOs = new ContactOSC();
			contactOs.setPartyNumber(null);
			contactOs.setFirstName(jsonObject.getString("firstName"));
			contactOs.setLastName(jsonObject.getString("lastName"));
			contactOs.setEmailAddress(jsonObject.getString("emailAddress"));
			
			createOSContact(new ObjectMapper().writeValueAsString(contactOs), jsonObject.getString("emailAddress"));
			
			Contact contact = GetMethods.getOSContactByEmail(contactOs.getEmailAddress());
			serializarObjectoLead(contact, contact.getEmail());
			
			return contact;	
			
		} catch(IOException | JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//Serializa un json a LeadOSC y crea el Lead
	private LeadOSC serializarObjectoLead(Contact contact, String email) {
		try {
			LeadOSC leadOS = new LeadOSC();
			leadOS.setName(contact.getFirstName() + contact.getLastName() + "_Lead");
			leadOS.setContactPartyNumber(contact.getId());
			
			return createOSLead(new ObjectMapper().writeValueAsString(leadOS), email);
			
		} catch(JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
