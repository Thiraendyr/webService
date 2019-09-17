package com.dtalavera.ejerciciosoa.crudaws.services;

import org.apache.http.HttpResponse;
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
	
	//Devuelve el Contact recibiendo el email
	/*
	public Contact getContact(String email) {
		return GetMethods.getElContactByEmail(ReplaceChars.transFormarLetras(email));
	}*/
	
	//Elimina un Contact recibiendo un email
	public String deleteElContact(String email) {
		try {
			Contact contacto = GetMethods.getElContactByEmail(email);
			if(contacto.getId() == 0L)
				return "ERROR: El contacto de email: " + email + " no existe...no se puede eliminar";
			
			CloseableHttpClient httpclient = HttpClientBuilder.create().build();
			HttpDelete httpDelete  = Auth.setDeleteContactHeaders("el", contacto.getId());
			
			httpclient.execute(httpDelete);
			
			httpclient.close();
			
			return "Eliminado con éxito..." + email;
			
		}catch(Exception e) {
			return "ERROR: No se ha podido eliminar";
		}
	}
	
	//Crea un Contact recibiendo el json de ContactEl serializado y su email
	public String createElContact(String json, String email) {
		try {
			Contact contacto = GetMethods.getElContactByEmail(email);
			if(contacto.getId() != 0L)
				return "Ya existe un contacto con ese email...";
			
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = Auth.setPostContactHeaders("el", json);
		    
			HttpResponse response = client.execute(httpPost);
			if(response.getStatusLine().getStatusCode() != 201) {
				return "ERROR: El email contiene caracteres raros";
			}
		    
		    client.close();
		    
		    return "Creado con éxito";
		} catch(Exception e) {
			return "ERROR: No se ha podido crear";}
	}
	
	//Serializa un contacto recibido por post a un ContactEl
	public String serializarObjecto(String jsonSend){
		try {
			JSONObject jsonObject = new JSONObject(jsonSend);
			
			ContactEl contactEl = new ContactEl();
			contactEl.setId(null);;
			contactEl.setFirstName(ReplaceChars.transFormarLetras(jsonObject.getString("firstName")));
			contactEl.setLastName(ReplaceChars.transFormarLetras(jsonObject.getString("lastName")));
			contactEl.setEmailAddress(ReplaceChars.transFormarLetras(jsonObject.getString("emailAddress")));
				
			String response = createElContact(new ObjectMapper().writeValueAsString(contactEl), ReplaceChars.transFormarLetras(jsonObject.getString("emailAddress")));
			
			return response;
		} catch(Exception e) {
			return "ERROR: No se ha podido crear";
		}
	}
}
