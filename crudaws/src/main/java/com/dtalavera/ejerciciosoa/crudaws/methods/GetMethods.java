package com.dtalavera.ejerciciosoa.crudaws.methods;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.dtalavera.ejerciciosoa.crudaws.config.Auth;
import com.dtalavera.ejerciciosoa.crudaws.entity.Contact;
import com.dtalavera.ejerciciosoa.crudaws.models.OSC.LeadOSC;

public class GetMethods {
	
	//Devuelve un Contact de Right Now recibiendo un json y un email
	private static Contact getRNContactByJson(String json, String email) throws JSONException {

		JSONObject jsonObject = new JSONObject(json);
	    JSONArray jsonArray = jsonObject.getJSONArray("items");
		if(!(jsonArray.length() == 0)) {
		    String[] name = jsonArray.getJSONObject(0).getString("lookupName").split(" ");
		    return setContact(jsonArray.getJSONObject(0).getString("id"), name[0], name[1], email);
		}
		
		return null;
	}
	
	//Devuelve un Contact de Right Now recibiendo un email
	public static Contact getRNContactByEmail(String email) throws ClientProtocolException, IOException, JSONException {
		
		CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = Auth.setGetContactHeaders("rn", ReplaceChars.transFormarLetras(email));
        
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(entity);
        
        client.close();
	        
        return GetMethods.getRNContactByJson(json, email);
	}
	
	//Devuelve un Contact de Oracle Sales Cloud recibiendo un json
	private static Contact getOSContactByJson(String json) throws JSONException {
		
		JSONObject jsonObject = new JSONObject(json);
	    JSONArray jsonArray = jsonObject.getJSONArray("items");
	    
		if(!(jsonArray.equals(""))) {
			return setContact(jsonArray.getJSONObject(0).getString("PartyNumber"), jsonArray.getJSONObject(0).getString("FirstName"), jsonArray.getJSONObject(0).getString("LastName"), jsonArray.getJSONObject(0).getString("EmailAddress"));
		}
			
		return null;
	}
	
	//Devuelve un Contact de Oracle Sales Cloud recibiendo un email
	public static Contact getOSContactByEmail(String email) throws ClientProtocolException, IOException, JSONException {

		CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = Auth.setGetContactHeaders("os", ReplaceChars.transFormarLetras(email));
        
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(entity);
        
        client.close();
        
        return GetMethods.getOSContactByJson(json);
        
	}
	
	//Devuelve un Contact de Eloqua recibiendo un json
	private static Contact getElContactByJson(String json) throws JSONException {
		if(!(json.equals(""))) {
			JSONObject jsonObject = new JSONObject(json);
		    JSONArray jsonArray = jsonObject.getJSONArray("elements");
		    return setContact(jsonArray.getJSONObject(0).getString("id"), "", "", jsonArray.getJSONObject(0).getString("emailAddress"));
		}
		
		return null;
	}
	
	//Devuelve un Contact de Eloqua recibiendo un email
	public static Contact getElContactByEmail(String email) throws ClientProtocolException, IOException, JSONException {

		CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = Auth.setGetContactHeaders("el", ReplaceChars.transFormarLetras(email));

        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(entity);
        
        client.close();
	        
        return GetMethods.getElContactByJson(json);
	}

	//Setea un Contact por parámetros
	private static Contact setContact(String id, String firstName, String lastName, String emailAddress) {
	    return new Contact() {{
			setId(Long.parseLong(id));
		    setFirstName(firstName);
		    setLastName(lastName);
		    setEmail(emailAddress);
	    }};
	}

	//Setea un LeadOSC por parámetros
	private static LeadOSC setLead(long id, String name) {
		return new LeadOSC() {{
			setContactPartyNumber(id);
			setName(name);
		}};
	}
	
	//Devuelve un LeadOSC recibiendo un json
	private static LeadOSC getOSLeadByJson(String json) throws JSONException {
		JSONObject jsonObject = new JSONObject(json);
	    JSONArray jsonArray = jsonObject.getJSONArray("items");
	    
		if(!(jsonArray.equals(""))) {
			return setLead(jsonArray.getJSONObject(0).getLong("LeadId"), jsonArray.getJSONObject(0).getString("PrimaryContactPartyName"));
		}
		
		return null;
	}
	
	//Devuelve un LeadOSC recibiendo un email
	public static LeadOSC getOSLeadByPrimaryContactEmailAddress(String email) throws ClientProtocolException, IOException, JSONException {

		CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = Auth.setGetContactHeaders("osLead", email);
        
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(entity);
        
        client.close();
        
        return GetMethods.getOSLeadByJson(json);
	        
	}
}
