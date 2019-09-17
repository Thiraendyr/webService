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
import com.dtalavera.ejerciciosoa.crudaws.models.RN.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@org.springframework.stereotype.Service
public class ServiceRN {
	
	public ServiceRN() {
		// TODO Auto-generated constructor stub
	}

//////////////////////////////////////Right Now
	
	//Devuelve un Contact recibiendo un email
	public Contact getContact(String email) {
		try {
			return GetMethods.getRNContactByEmail(ReplaceChars.transFormarLetras(email));
			
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//Elimina un Contact recibiendo su email
	public Void deleteRNContact(String email) {
		try {
			Contact contacto = GetMethods.getRNContactByEmail(email);
			if(contacto.getId() != 0L){
			
				CloseableHttpClient httpclient = HttpClientBuilder.create().build();
				HttpDelete httpDelete = Auth.setDeleteContactHeaders("rn", contacto.getId());
				
				httpclient.execute(httpDelete);
				
				httpclient.close();
				
			}
			
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//Crea un contacto recibiendo un json de su ContactRN y su email
	public Contact createRNContact(String json, String email) {
		try {
			Contact contacto = GetMethods.getRNContactByEmail(email);
			if(contacto.getId() == 0L) {
			
				CloseableHttpClient client = HttpClients.createDefault();
				HttpPost httpPost  = Auth.setPostContactHeaders("rn", json);
			    
				HttpResponse response = client.execute(httpPost);
				
				if(response.getStatusLine().getStatusCode() == 201) {
				    client.close();
					return GetMethods.getRNContactByEmail(email);
				}
				
			}
			
		}catch(IOException | JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//Serializa un contacto recibido por json a un ContactRN
	public Contact serializarObjecto(String jsonReceived) {
		try {
			JSONObject jsonObject = new JSONObject(jsonReceived);
			
			ContactRN contactRn = new ContactRN();
			contactRn.setId(null);
			contactRn.setName(new Name(ReplaceChars.transFormarLetras(jsonObject.getString("firstName")),ReplaceChars.transFormarLetras(jsonObject.getString("lastName"))));
			contactRn.setEmails(new Emails(ReplaceChars.transFormarLetras(jsonObject.getString("emailAddress")), new AddressType(0)));
			
			return createRNContact(new ObjectMapper().writeValueAsString(contactRn),ReplaceChars.transFormarLetras(jsonObject.getString("emailAddress")));
			
		}catch(JSONException | JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}

}
