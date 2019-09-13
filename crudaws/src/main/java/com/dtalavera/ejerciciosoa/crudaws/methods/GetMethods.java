package com.dtalavera.ejerciciosoa.crudaws.methods;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.dtalavera.ejerciciosoa.crudaws.config.Auth;
import com.dtalavera.ejerciciosoa.crudaws.entity.Contact;
import com.dtalavera.ejerciciosoa.crudaws.models.OSC.LeadOSC;

public class GetMethods {
	
	//Devuelve un Contact de Right Now recibiendo un json y un email
	private static Contact getRNContactByJson(String json, String email) {
		Contact contacto = new Contact();
		try {
			JSONObject jsonObject = new JSONObject(json);
		    JSONArray jsonArray = jsonObject.getJSONArray("items");
			if(!(jsonArray.length() == 0)) {
			    String[] name = jsonArray.getJSONObject(0).getString("lookupName").split(" ");
			    contacto = setContact(contacto, jsonArray.getJSONObject(0).getString("id"), name[0], name[1], email);
			}
		}catch(Exception e) {
		}
		
		return contacto;
	}
	
	//Devuelve un Contact de Right Now recibiendo un email
	public static Contact getRNContactByEmail(String email) {
		Contact contacto = new Contact();
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
	        HttpGet request = Auth.setGetContactHeaders("rn", ReplaceChars.transFormarLetras(email));
	        
	        HttpResponse response = client.execute(request);
	        HttpEntity entity = response.getEntity();
	        String json = EntityUtils.toString(entity);
	        contacto = GetMethods.getRNContactByJson(json, email);
	        
	        client.close();
	        
		} catch(Exception e) {
        }
        return contacto;
	}
	
	//Devuelve un Contact de Oracle Sales Cloud recibiendo un json
	private static Contact getOSContactByJson(String json) {
		Contact contacto = new Contact();
		try {
			JSONObject jsonObject = new JSONObject(json);
		    JSONArray jsonArray = jsonObject.getJSONArray("items");
			if(!(jsonArray.equals(""))) {
				contacto = setContact(contacto, jsonArray.getJSONObject(0).getString("PartyNumber"), jsonArray.getJSONObject(0).getString("FirstName"), jsonArray.getJSONObject(0).getString("LastName"), jsonArray.getJSONObject(0).getString("EmailAddress"));
			}
		}catch(Exception e) {
		}
		return contacto;
	}
	
	//Devuelve un Contact de Oracle Sales Cloud recibiendo un email
	public static Contact getOSContactByEmail(String email) {
		Contact contacto = new Contact();
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
	        HttpGet request = Auth.setGetContactHeaders("os", ReplaceChars.transFormarLetras(email));
	        
	        HttpResponse response = client.execute(request);
	        HttpEntity entity = response.getEntity();
	        String json = EntityUtils.toString(entity);
	        contacto = GetMethods.getOSContactByJson(json);
	        
	        client.close();
	        
		} catch(Exception e) {
        }
		
        return contacto;
	}
	
	//Devuelve un Contact de Eloqua recibiendo un json
	private static Contact getElContactByJson(String json) {
		Contact contacto = new Contact();
		if(!(json.equals(""))) {
			try {
				JSONObject jsonObject = new JSONObject(json);
			    JSONArray jsonArray = jsonObject.getJSONArray("elements");
			    contacto = setContact(contacto, jsonArray.getJSONObject(0).getString("id"), "", "", jsonArray.getJSONObject(0).getString("emailAddress"));
			    
			}catch(Exception e) {
			}
		}
		
		return contacto;
	}
	
	//Devuelve un Contact de Eloqua recibiendo un email
	public static Contact getElContactByEmail(String email) {
		Contact contacto = new Contact();
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
	        HttpGet request = Auth.setGetContactHeaders("el", ReplaceChars.transFormarLetras(email));

	        HttpResponse response = client.execute(request);
	        HttpEntity entity = response.getEntity();
	        String json = EntityUtils.toString(entity);
	        contacto = GetMethods.getElContactByJson(json);
	        
	        client.close();
	        
		} catch(Exception e) {
        }
        return contacto;
	}

	//Setea un Contact por parámetros
	private static Contact setContact(Contact contacto, String id, String firstName, String lastName, String emailAddress) {
		contacto.setId(Long.parseLong(id));
	    contacto.setFirstName(firstName);
	    contacto.setLastName(lastName);
	    contacto.setEmail(emailAddress);
	    return contacto;
	}

	//Setea un LeadOSC por parámetros
	private static LeadOSC setLead(LeadOSC lead, long id, String name) {
		lead.setContactPartyNumber(id);
		lead.setName(name);
		return lead;
	}
	
	//Devuelve un LeadOSC recibiendo un json
	private static LeadOSC getOSLeadByJson(String json) {
		LeadOSC lead = new LeadOSC();
		try {
			JSONObject jsonObject = new JSONObject(json);
		    JSONArray jsonArray = jsonObject.getJSONArray("items");
			if(!(jsonArray.equals(""))) {
				lead = setLead(lead, jsonArray.getJSONObject(0).getLong("LeadId"), jsonArray.getJSONObject(0).getString("PrimaryContactPartyName"));
			}
		}catch(Exception e) {
		}
		return lead;
	}
	
	//Devuelve un LeadOSC recibiendo un email
	public static LeadOSC getOSLeadByPrimaryContactEmailAddress(String email) {
		LeadOSC lead = new LeadOSC();
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
	        HttpGet request = Auth.setGetContactHeaders("osLead", email);
	        
	        HttpResponse response = client.execute(request);
	        HttpEntity entity = response.getEntity();
	        String json = EntityUtils.toString(entity);
	        lead = GetMethods.getOSLeadByJson(json);
	        
	        client.close();
	        
		} catch(Exception e) {
        }
        return lead;
	}
}
