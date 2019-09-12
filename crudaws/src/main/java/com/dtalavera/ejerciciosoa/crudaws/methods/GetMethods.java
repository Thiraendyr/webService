package com.dtalavera.ejerciciosoa.crudaws.methods;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.dtalavera.ejerciciosoa.crudaws.config.Auth;
import com.dtalavera.ejerciciosoa.crudaws.entity.Contact;

public class GetMethods {
	
	public static Contact getRNContactByJson(String json, String email) {
		Contact contacto = new Contact();
		try {
			JSONObject jsonObject = new JSONObject(json);
		    JSONArray jsonArray = jsonObject.getJSONArray("items");
			if(!(jsonArray.length() == 0)) {
			    String[] name = jsonArray.getJSONObject(0).getString("lookupName").split(" ");
			    contacto = setContact(contacto, jsonArray.getJSONObject(0).getString("id"), name[0], name[1], email);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return contacto;
	}
	
	public static Contact getRNContactByEmail(String email) {
		Contact contacto = new Contact();
		try {
			System.out.println(email);
			HttpClient client = HttpClientBuilder.create().build();
	        HttpGet request = Auth.setGetHeaders("rn", email);
	        HttpResponse response = client.execute(request);
	        HttpEntity entity = response.getEntity();
	        String json = EntityUtils.toString(entity);
	        contacto = GetMethods.getRNContactByJson(json, email);
		} catch(Exception e) {
        	e.printStackTrace();
        }
        return contacto;
	}
	
	public static Contact getOSContactByJson(String json) {
		Contact contacto = new Contact();
		if(!(json.equals(""))) {
			try {
				JSONObject jsonObject = new JSONObject(json);
			    JSONArray jsonArray = jsonObject.getJSONArray("items");
			    contacto = setContact(contacto, jsonArray.getJSONObject(0).getString("PartyNumber"), jsonArray.getJSONObject(0).getString("FirstName"), jsonArray.getJSONObject(0).getString("LastName"), jsonArray.getJSONObject(0).getString("EmailAddress"));
			    
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return contacto;
	}
	
	public static Contact getOSContactByEmail(String email) {
		Contact contacto = new Contact();
		try {
			HttpClient client = HttpClientBuilder.create().build();
	        HttpGet request = Auth.setGetHeaders("os", email);
	        
	        HttpResponse response = client.execute(request);
	        HttpEntity entity = response.getEntity();
	        String json = EntityUtils.toString(entity);
	        contacto = GetMethods.getOSContactByJson(json);
		} catch(Exception e) {
        	e.printStackTrace();
        }
        return contacto;
	}
	
	private static Contact getElContactByJson(String json) {
		Contact contacto = new Contact();
		if(!(json.equals(""))) {
			try {
				JSONObject jsonObject = new JSONObject(json);
			    JSONArray jsonArray = jsonObject.getJSONArray("elements");
			    contacto = setContact(contacto, jsonArray.getJSONObject(0).getString("id"), "", "", jsonArray.getJSONObject(0).getString("emailAddress"));
			    
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return contacto;
	}
	
	public static Contact getElContactByEmail(String email) {
		Contact contacto = new Contact();
		try {
			HttpClient client = HttpClientBuilder.create().build();
	        HttpGet request = Auth.setGetHeaders("el", email);

	        HttpResponse response = client.execute(request);
	        HttpEntity entity = response.getEntity();
	        String json = EntityUtils.toString(entity);
	        contacto = GetMethods.getElContactByJson(json);
		} catch(Exception e) {
        	e.printStackTrace();
        }
        return contacto;
	}

	private static Contact setContact(Contact contacto, String id, String firstName, String lastName, String emailAddress) {
		contacto.setId(Long.parseLong(id));
	    contacto.setFirstName(firstName);
	    contacto.setLastName(lastName);
	    contacto.setEmail(emailAddress);
	    return contacto;
	}
}
