package com.dtalavera.ejerciciosoa.crudaws.config;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

public class Auth {

	public static HttpPost setPostContactHeaders(String api, String json) throws UnsupportedEncodingException {
		HttpPost hp = null;
		switch(api) {
		case "rn":
			hp = new HttpPost(Config.getUrlRN() + "/services/rest/connect/v1.3/contacts");
			System.out.println(json);
			hp.setHeader(HttpHeaders.AUTHORIZATION, new String("Basic " + Base64.getEncoder().encodeToString((Config.getUsrRN() + ":" + Config.getPwdRN()).getBytes())));
			break;
		case "el":
			hp = new HttpPost(Config.getUrlEl() + "/api/REST/1.0/data/contact");
			hp.setHeader(HttpHeaders.AUTHORIZATION, new String("Basic " + Base64.getEncoder().encodeToString((Config.getUsrEl() + ":" + Config.getPwdEl()).getBytes())));
			break;
		case "os":
			hp = new HttpPost(Config.getUrlOS() + "/crmRestApi/resources/latest/contacts");
			hp.setHeader(HttpHeaders.AUTHORIZATION, new String("Basic " + Base64.getEncoder().encodeToString((Config.getUsrOS() + ":" + Config.getPwdOS()).getBytes())));
			break;
		case "osLead":
			hp = new HttpPost(Config.getUrlOS() + "/crmRestApi/resources/latest/leads");
			hp.setHeader(HttpHeaders.AUTHORIZATION, new String("Basic " + Base64.getEncoder().encodeToString((Config.getUsrOS() + ":" + Config.getPwdOS()).getBytes())));
			break;
		}
		hp.setEntity(new StringEntity(json));
		hp.setHeader("Accept", "application/json");
		hp.setHeader("Content-type", "application/json");
	    return hp;
	}
	
	public static HttpDelete setDeleteContactHeaders(String api, String id) {
		HttpDelete hd = null;
		switch(api) {
		case "rn":
			hd = new HttpDelete(Config.getUrlRN() + "/services/rest/connect/v1.3/contacts/" + id);
			hd.setHeader(HttpHeaders.AUTHORIZATION, new String("Basic " + Base64.getEncoder().encodeToString((Config.getUsrRN() + ":" + Config.getPwdRN()).getBytes())));
			break;
		case "el":
			hd = new HttpDelete(Config.getUrlEl() + "/api/REST/1.0/data/contact/" + id);
			hd.setHeader(HttpHeaders.AUTHORIZATION, new String("Basic " + Base64.getEncoder().encodeToString((Config.getUsrEl() + ":" + Config.getPwdEl()).getBytes())));
			break;
		case "os":
			hd = new HttpDelete(Config.getUrlOS() + "/crmRestApi/resources/latest/contacts/" + id);
			hd.setHeader(HttpHeaders.AUTHORIZATION, new String("Basic " + Base64.getEncoder().encodeToString((Config.getUsrOS() + ":" + Config.getPwdOS()).getBytes())));
			break;
		case "osLead":
			hd = new HttpDelete(Config.getUrlOS() + "/crmRestApi/resources/latest/leads/" + id);
			hd.setHeader(HttpHeaders.AUTHORIZATION, new String("Basic " + Base64.getEncoder().encodeToString((Config.getUsrOS() + ":" + Config.getPwdOS()).getBytes())));
			break;
		}
	    return hd;
	}
	
	public static HttpGet setGetContactHeaders(String api, String email) {
		HttpGet hg = null;
		switch(api) {
		case "rn":
			hg = new HttpGet(Config.getUrlRN() + "/services/rest/connect/v1.3/contacts?q=emails.address='" + email + "'");
			hg.setHeader(HttpHeaders.AUTHORIZATION, new String("Basic " + Base64.getEncoder().encodeToString((Config.getUsrRN() + ":" + Config.getPwdRN()).getBytes())));
			break;
		case "el":
			hg = new HttpGet(Config.getUrlEl() + "/API/REST/1.0/data/contacts?search=emailAddress='" + email + "'");
			hg.setHeader(HttpHeaders.AUTHORIZATION, new String("Basic " + Base64.getEncoder().encodeToString((Config.getUsrEl() + ":" + Config.getPwdEl()).getBytes())));
			break;
		case "os":
			hg = new HttpGet(Config.getUrlOS() + "/crmRestApi/resources/latest/contacts?q=EmailAddress=" + email);
			hg.setHeader(HttpHeaders.AUTHORIZATION, new String("Basic " + Base64.getEncoder().encodeToString((Config.getUsrOS() + ":" + Config.getPwdOS()).getBytes())));
			break;
		case "osLead":
			hg = new HttpGet(Config.getUrlOS() + "/crmRestApi/resources/latest/leads?q=PrimaryContactEmailAddress=" + email);
			hg.setHeader(HttpHeaders.AUTHORIZATION, new String("Basic " + Base64.getEncoder().encodeToString((Config.getUsrOS() + ":" + Config.getPwdOS()).getBytes())));
			break;
		}
		hg.addHeader("accept", "application/json");
	    return hg;
	}
}
