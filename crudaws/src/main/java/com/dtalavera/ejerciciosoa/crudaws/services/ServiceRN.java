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
import com.dtalavera.ejerciciosoa.crudaws.models.RN.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@org.springframework.stereotype.Service
public class ServiceRN{
	
	public ServiceRN() {
		// TODO Auto-generated constructor stub
	}

//////////////////////////////////////Right Now
	
	//Devuelve un Contact recibiendo un email
	public Contact getContact(String email) {
		return GetMethods.getRNContactByEmail(ReplaceChars.transFormarLetras(email));
	}
	
	//Elimina un Contact recibiendo su email
	public String deleteRNContact(String email) {
		try {
			Contact contacto = GetMethods.getRNContactByEmail(email);
			if(contacto.getId() == 0L)
				return "ERROR: El contacto de email: " + email + " no existe...no se puede eliminar";
			
			CloseableHttpClient httpclient = HttpClientBuilder.create().build();
			HttpDelete httpDelete = Auth.setDeleteContactHeaders("rn", contacto.getId());
			
			httpclient.execute(httpDelete);
			
			httpclient.close();
			
			return "Eliminado con éxito..." + email;
			
		}catch(Exception e) {
			return "ERROR: No se ha podido eliminar";
		}
	}
	
	//Crea un contacto recibiendo un json de su ContactRN y su email
	public String createRNContact(String json, String email) {
		try {
			Contact contacto = GetMethods.getRNContactByEmail(email);
			if(contacto.getId() != 0L)
				return "Ese contacto ya existe";
			
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost  = Auth.setPostContactHeaders("rn", json);
		    
			HttpResponse response = client.execute(httpPost);
			if(response.getStatusLine().getStatusCode() != 201) {
				return "ERROR: El email contiene caracteres raros";
			}
		    client.close();
		    
		    return "Creado con éxito";
		}catch(Exception e) {
			return "ERROR: No se ha podido crear";
		}
	}
	
	//Serializa un contacto recibido por json a un ContactRN
	public String serializarObjecto(String jsonSend){
		try {
			JSONObject jsonObject = new JSONObject(jsonSend);
			
			ContactRN contactRn = new ContactRN();
			contactRn.setId(null);
			contactRn.setName(new Name(ReplaceChars.transFormarLetras(jsonObject.getString("firstName")),ReplaceChars.transFormarLetras(jsonObject.getString("lastName"))));
			contactRn.setEmails(new Emails(ReplaceChars.transFormarLetras(jsonObject.getString("emailAddress")), new AddressType(0)));
				
			String response = createRNContact(new ObjectMapper().writeValueAsString(contactRn),ReplaceChars.transFormarLetras(jsonObject.getString("emailAddress")));
			
			return response;
		}catch(Exception e) {
			return "ERROR: No se ha podido crear";
		}
	}

}
