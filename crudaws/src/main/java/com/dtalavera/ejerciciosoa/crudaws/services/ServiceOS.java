package com.dtalavera.ejerciciosoa.crudaws.services;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.dtalavera.ejerciciosoa.crudaws.config.Auth;
import com.dtalavera.ejerciciosoa.crudaws.entity.Contact;
import com.dtalavera.ejerciciosoa.crudaws.methods.GetMethods;
import com.dtalavera.ejerciciosoa.crudaws.methods.ReplaceChars;
import com.dtalavera.ejerciciosoa.crudaws.models.OSC.ContactOSC;
import com.dtalavera.ejerciciosoa.crudaws.models.OSC.LeadOSC;
import com.fasterxml.jackson.databind.ObjectMapper;

@org.springframework.stereotype.Service
public class ServiceOS{
	
	public ServiceOS() {
		// TODO Auto-generated constructor stub
	}

//////////////////////////////////////Oracle Sales Cloud
	public Contact getContact(String email) {
		return GetMethods.getOSContactByEmail(ReplaceChars.transFormarLetras(email));
	}
	
	public String deleteOSContact(String email) {
		try {
			Contact contacto = GetMethods.getOSContactByEmail(email);
			if(contacto.getId() == 0L)
				return "ERROR: El contacto de email: " + email + " no existe...no se puede eliminar";
			
			CloseableHttpClient httpclient = HttpClientBuilder.create().build();
			HttpDelete httpDelete  = Auth.setDeleteContactHeaders("os", String.valueOf(contacto.getId()));
			
			httpclient.execute(httpDelete);
			
			deleteOSLead(contacto.getEmail());

			httpclient.close();
			
			return "Eliminado con éxito..." + email + " y su lead " + contacto.getFirstName() + contacto.getLastName() + "_Lead";
			
		}catch(Exception e) {
			return "ERROR: No se ha podido eliminar";
		}
	}
	
	private static boolean deleteOSLead(String email) {
		try {
			LeadOSC lead = GetMethods.getOSLeadByPrimaryContactEmailAddress(email);
			if((long) lead.getContactPartyNumber() == 0)
				return false;
			
			CloseableHttpClient httpclient = HttpClientBuilder.create().build();
			HttpDelete httpDelete  = Auth.setDeleteContactHeaders("osLead", String.valueOf(lead.getContactPartyNumber()));
			
			httpclient.execute(httpDelete);

			httpclient.close();
			
			return true;
			
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean createOSContact(String json, String email) {
		try {
			Contact contacto = GetMethods.getOSContactByEmail(email);
			if(contacto.getId() != 0L)
				return false;
			
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost  = Auth.setPostContactHeaders("os", json);
			
			System.out.println("Contacto: " + client.execute(httpPost));
			
		    client.close();
		    
		    return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	private boolean createOSLead(String json) {
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost  = Auth.setPostContactHeaders("osLead", json);
			
			System.out.println("Lead: " + client.execute(httpPost));
		    
		    client.close();
		    
		    return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public String serializarObjectoContact(String jsonSend){
		try {
			JSONObject jsonObject = new JSONObject(jsonSend);
			
			ContactOSC contactOs = new ContactOSC();
			contactOs.setPartyNumber(null);
			contactOs.setFirstName(ReplaceChars.transFormarLetras(jsonObject.getString("firstName")));
			contactOs.setLastName(ReplaceChars.transFormarLetras(jsonObject.getString("lastName")));
			contactOs.setEmailAddress(ReplaceChars.transFormarLetras(jsonObject.getString("emailAddress")));
				
			if(createOSContact(new ObjectMapper().writeValueAsString(contactOs), ReplaceChars.transFormarLetras(jsonObject.getString("emailAddress")))) {
				Contact c = GetMethods.getOSContactByEmail(contactOs.getEmailAddress());
				serializarObjectoLead(c);
				return "Creado con éxito";
			}
			else {
				return "No se admiten correos repetidos";
			}
			
		}catch(Exception e) {
			return "ERROR: No se ha podido crear";
		}
	}
	
	public boolean serializarObjectoLead(Contact contact){
		try {
			LeadOSC leadOS = new LeadOSC();
			leadOS.setName(contact.getFirstName() + contact.getLastName() + "_Lead");
			leadOS.setContactPartyNumber(contact.getId());
			
			boolean response = createOSLead(new ObjectMapper().writeValueAsString(leadOS));
			
			System.out.println(new ObjectMapper().writeValueAsString(leadOS));
			
			return response;
		}catch(Exception e) {
			return false;
		}
	}

}
