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
	//Busca un Contact por email
	public Contact getContact(String email) {
		return GetMethods.getOSContactByEmail(ReplaceChars.transFormarLetras(email));
	}
	
	//Elimina un Contact por email
	public String deleteOSContact(String email) {
		try {
			Contact contacto = GetMethods.getOSContactByEmail(email);
			if(contacto.getId() == 0L)
				return "ERROR: El contacto de email: " + email + " no existe...no se puede eliminar";
			
			CloseableHttpClient httpclient = HttpClientBuilder.create().build();
			HttpDelete httpDelete  = Auth.setDeleteContactHeaders("os", contacto.getId());
			
			httpclient.execute(httpDelete);
			
			deleteOSLeadsByEmail(email);

			httpclient.close();
			
			return "Eliminado con éxito..." + email + " y su lead " + contacto.getFirstName() + contacto.getLastName() + "_Lead";
			
		}catch(Exception e) {
			return "ERROR: No se ha podido eliminar";
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
			
		}catch(Exception e) {
			return false;
		}
	}
	
	//Hace post del contacto desde el ContactOSC recibido por json
	private String createOSContact(String json, String email) {
		try {
			Contact contacto = GetMethods.getOSContactByEmail(email);
			if(contacto.getId() != 0L)
				return "Ya existe un contacto con ese email...";
			
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost  = Auth.setPostContactHeaders("os", json);
			
			client.execute(httpPost);
			
		    client.close();
		    
		    return "Se ha creado con éxito el nuevo contacto";
		}catch(Exception e) {
			return "ERROR: No se ha podido crear el contacto";
		}
	}
	
	//Realiza un post desde el json del LeadOSC
	private String createOSLead(String json, String email) {
		try {
			LeadOSC lead = GetMethods.getOSLeadByPrimaryContactEmailAddress(email);
			if(lead.getContactPartyNumber() != 0L)
				return " ya existe un lead asociado a ese email...";
			
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost  = Auth.setPostContactHeaders("osLead", json);
			
			client.execute(httpPost);
		    
		    client.close();
		    
		    return " se ha creado un lead asociado a ese contacto";
		}catch(Exception e) {
			return "ERROR: no se ha podido crear el lead";
		}
	}
	
	//Serializa un json a ContactOSC y crea el contacto
	public String serializarObjectoContact(String jsonSend){
		try {
			JSONObject jsonObject = new JSONObject(jsonSend);
			
			ContactOSC contactOs = new ContactOSC();
			contactOs.setPartyNumber(null);
			contactOs.setFirstName(ReplaceChars.transFormarLetras(jsonObject.getString("firstName")));
			contactOs.setLastName(ReplaceChars.transFormarLetras(jsonObject.getString("lastName")));
			contactOs.setEmailAddress(ReplaceChars.transFormarLetras(jsonObject.getString("emailAddress")));
			
			String responseContacto = createOSContact(new ObjectMapper().writeValueAsString(contactOs), ReplaceChars.transFormarLetras(jsonObject.getString("emailAddress")));
			
			Contact c = GetMethods.getOSContactByEmail(contactOs.getEmailAddress());
			String responseLead = serializarObjectoLead(c, c.getEmail());
			
			return responseContacto + responseLead;
		
			//return "No se admiten correos repetidos";
			
			
		}catch(Exception e) {
			return "ERROR: No se ha podido crear";
		}
	}
	
	//Serializa un json a LeadOSC y crea el Lead
	public String serializarObjectoLead(Contact contact, String email){
		try {
			LeadOSC leadOS = new LeadOSC();
			leadOS.setName(contact.getFirstName() + contact.getLastName() + "_Lead");
			leadOS.setContactPartyNumber(contact.getId());
			
			String response = createOSLead(new ObjectMapper().writeValueAsString(leadOS), email);
			
			return response;
		}catch(Exception e) {
			return "ERROR: No se ha podido crear el lead";
		}
	}

}
