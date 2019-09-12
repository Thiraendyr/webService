package com.dtalavera.ejerciciosoa.crudaws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dtalavera.ejerciciosoa.crudaws.entity.Contact;
import com.dtalavera.ejerciciosoa.crudaws.services.*;

@RestController
public class Endpoints {

	@Autowired
	ServiceAll serviceAll;
	
	@Autowired
	ServiceRN serviceRN;
	
	@Autowired
	ServiceEloqua serviceEl;
	
	@Autowired
	ServiceOS serviceOS;
	
//////////////////////////////////////Operaciones simultaneas para las tres plataformas
	
	@RequestMapping(value = "all/contacts/{email}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteFromAll(@PathVariable("email") String email) {
		return new ResponseEntity<String>(serviceAll.deleteFromAll(email), HttpStatus.OK);
	}
	
	@RequestMapping(value = "all/contacts", method = RequestMethod.POST)
	public ResponseEntity<String> createAtAll(@RequestBody String c) {
		return new ResponseEntity<String>(serviceAll.createAtAll(c), HttpStatus.OK);
	}
	
//////////////////////////////////////Right Now
	@RequestMapping(value = "rn/contacts/{email}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteRNContact(@PathVariable("email") String email) {
		return new ResponseEntity<String>(serviceRN.deleteRNContact(email), HttpStatus.OK);
	}
	
	@RequestMapping(value = "rn/contacts/{email}", method = RequestMethod.GET)
	public ResponseEntity<Contact> getRNContact(@PathVariable("email") String email) {
		return new ResponseEntity<Contact>(serviceRN.getContact(email), HttpStatus.OK);
	}
	
	@RequestMapping(value = "rn/contacts", method = RequestMethod.POST)
	public ResponseEntity<String> createRNContact(@RequestBody String c) {
		return new ResponseEntity<String>(serviceRN.serializarObjecto(c), HttpStatus.OK);
	}
	
//////////////////////////////////////Eloqua
	@RequestMapping(value = "el/contacts/{email}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteElContact(@PathVariable("email") String email) {
		return new ResponseEntity<String>(serviceEl.deleteElContact(email), HttpStatus.OK);
	}
	
	@RequestMapping(value = "el/contacts/{email}", method = RequestMethod.GET)
	public ResponseEntity<Contact> getElContact(@PathVariable("email") String email) {
		return new ResponseEntity<Contact>(serviceEl.getContact(email), HttpStatus.OK);
	}
	
	@RequestMapping(value = "el/contacts", method = RequestMethod.POST)
	public ResponseEntity<String> createElContact(@RequestBody String c) {
		return new ResponseEntity<String>(serviceEl.serializarObjecto(c), HttpStatus.OK);
	}
	
//////////////////////////////////////Oracle Sales Cloud
	@RequestMapping(value = "os/contacts/{email}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteOSContact(@PathVariable("email") String email) {
		return new ResponseEntity<String>(serviceOS.deleteOSContact(email), HttpStatus.OK);
	}
	
	@RequestMapping(value = "os/contacts/{email}", method = RequestMethod.GET)
	public ResponseEntity<Contact> getOSContact(@PathVariable("email") String email) {
		return new ResponseEntity<Contact>(serviceOS.getContact(email), HttpStatus.OK);
	}
	
	@RequestMapping(value = "os/contacts", method = RequestMethod.POST)
	public ResponseEntity<String> createOSContact(@RequestBody String c) {
		return new ResponseEntity<String>(serviceOS.serializarObjectoContact(c), HttpStatus.OK);
	}
}
