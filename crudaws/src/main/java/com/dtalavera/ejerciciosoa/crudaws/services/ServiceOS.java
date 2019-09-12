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
import com.dtalavera.ejerciciosoa.crudaws.models.OSC.ContactOSC;
import com.fasterxml.jackson.databind.ObjectMapper;

@org.springframework.stereotype.Service
public class ServiceOS{
	
	public ServiceOS() {
		// TODO Auto-generated constructor stub
	}

//////////////////////////////////////Oracle Sales Cloud
	public Contact getContact(String email) {
		return GetMethods.getOSContactByEmail(email);
	}
	
	public String deleteOSContact(String email) {
		try {
			Contact contacto = GetMethods.getOSContactByEmail(email);
			if(contacto.getId() == 0L)
				return "ERROR: El contacto de email: " + email + " no existe...no se puede eliminar";
			
			CloseableHttpClient httpclient = HttpClientBuilder.create().build();
			HttpDelete httpDelete  = Auth.setDeleteHeaders("os", String.valueOf(contacto.getId()));
			
			httpclient.execute(httpDelete);

			httpclient.close();
			
			return "Eliminado con éxito..." + email;
			
		}catch(Exception e) {e.printStackTrace();return "ERROR: No se ha podido eliminar";}
	}
	
	public String createOSContact(String json, ContactOSC contactoOS) {
		try {
			Contact contacto = GetMethods.getOSContactByEmail(contactoOS.getEmailAddress());
			if(contacto.getId() != 0L)
				return "Ese contacto ya existe";
			
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost  = Auth.setPostHeaders("os", json);
			
		    client.execute(httpPost);
		    
		    client.close();
		    
		    return "Creado con éxito";
		}catch(Exception e) {
			e.printStackTrace();
			return "ERROR: No se ha podido crear";
		}
	}
	
	public String serializarObjecto(String jsonSend){
		try {
			JSONObject jsonObject = new JSONObject(jsonSend);
			
			ContactOSC contactOs = new ContactOSC();
			contactOs.setPartyNumber(null);
			contactOs.setFirstName(jsonObject.getString("firstName"));
			contactOs.setLastName(jsonObject.getString("lastName"));
			contactOs.setEmailAddress(jsonObject.getString("emailAddress"));
				
			return createOSContact(new ObjectMapper().writeValueAsString(contactOs), contactOs);
		}catch(Exception e) {
			e.printStackTrace();
			return "ERROR: No se ha podido crear";
		}
	}

}
