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
import com.dtalavera.ejerciciosoa.crudaws.models.El.ContactEl;
import com.fasterxml.jackson.databind.ObjectMapper;

@org.springframework.stereotype.Service
public class ServiceEloqua{
	
	public ServiceEloqua() {
		// TODO Auto-generated constructor stub
	}

//////////////////////////////////////Eloqua	
	public Contact getContact(String email) {
		return GetMethods.getElContactByEmail(email);
	}
	
	public String deleteElContact(String email) {
		try {
			email = ReplaceChars.JsonTransformerURI(email);
			Contact contacto = GetMethods.getElContactByEmail(email);
			if(contacto.getId() == 0L)
				return "ERROR: El contacto de email: " + email + " no existe...no se puede eliminar";
			
			CloseableHttpClient httpclient = HttpClientBuilder.create().build();
			HttpDelete httpDelete  = Auth.setDeleteHeaders("el", String.valueOf(contacto.getId()));
			
			httpclient.execute(httpDelete);
			
			httpclient.close();
			
			return "Eliminado con éxito..." + email;
			
		}catch(Exception e) {e.printStackTrace();return "ERROR: No se ha podido eliminar";}
	}
	
	public String createElContact(String json, ContactEl contactoEl) {
		try {
			Contact contacto = GetMethods.getElContactByEmail(contactoEl.getEmailAddress());
			if(contacto.getId() != 0L)
				return "Ese contacto ya existe";
			
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = Auth.setPostHeaders("el", json);
		    
		    client.execute(httpPost);
		    
		    client.close();
		    
		    return "Creado con éxito";
		}catch(Exception e) {e.printStackTrace();return "ERROR: No se ha podido crear";}
	}
	
	public String serializarObjecto(String jsonSend){
		try {
			String json = ReplaceChars.stripDiacritics(jsonSend);
			JSONObject jsonObject = new JSONObject(json);
			
			ContactEl contactEl = new ContactEl();
			contactEl.setId(null);;
			contactEl.setFirstName(jsonObject.getString("firstName"));
			contactEl.setLastName(jsonObject.getString("lastName"));
			contactEl.setEmailAddress(jsonObject.getString("emailAddress"));
				
			return createElContact(new ObjectMapper().writeValueAsString(contactEl), contactEl);
		}catch(Exception e) {
			e.printStackTrace();
			return "ERROR: No se ha podido crear";
		}
	}
}
