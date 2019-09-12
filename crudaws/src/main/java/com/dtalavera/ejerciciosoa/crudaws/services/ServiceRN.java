package com.dtalavera.ejerciciosoa.crudaws.services;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
	public Contact getContact(String email) {
		return GetMethods.getRNContactByEmail(email);
	}
	
	public String deleteRNContact(String email) {
		try {
			//email = ReplaceChars.stripDiacritics(email);
			Contact contacto = GetMethods.getRNContactByEmail(email);
			if(contacto.getId() == 0L)
				return "ERROR: El contacto de email: " + email + " no existe...no se puede eliminar";
			
			CloseableHttpClient httpclient = HttpClientBuilder.create().build();
			HttpDelete httpDelete = Auth.setDeleteHeaders("rn", String.valueOf(contacto.getId()));
			
			httpclient.execute(httpDelete);
			
			httpclient.close();
			
			return "Eliminado con éxito..." + email;
			
		}catch(Exception e) {e.printStackTrace();return "ERROR: No se ha podido eliminar";}
	}
	
	public String createRNContact(String json, String email) {
		try {
			Contact contacto = GetMethods.getRNContactByEmail(email);
			if(contacto.getId() != 0L)
				return "Ese contacto ya existe";
			
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost  = Auth.setPostHeaders("rn", json);
		    
		    //client.execute(httpPost);
		    System.out.println(EntityUtils.toString(client.execute(httpPost).getEntity()));
		    client.close();
		    
		    return "Creado con éxito";
		}catch(Exception e) {e.printStackTrace();return "ERROR: No se ha podido crear";}
	}
	
	public String serializarObjecto(String jsonSend){
		try {
			JSONObject jsonObject = new JSONObject(jsonSend);
			
			ContactRN contactRn = new ContactRN();
			contactRn.setId(null);
			contactRn.setName(new Name(jsonObject.getString("firstName"),jsonObject.getString("lastName")));
			contactRn.setEmails(new Emails(ReplaceChars.JsonTransformerURI(jsonObject.getString("emailAddress")), new AddressType(0)));
			
			System.out.println(contactRn.toString());
				
			return createRNContact(new ObjectMapper().writeValueAsString(contactRn),jsonObject.getString("emailAddress"));
		}catch(Exception e) {
			e.printStackTrace();
			return "ERROR: No se ha podido crear";
		}
	}

}
