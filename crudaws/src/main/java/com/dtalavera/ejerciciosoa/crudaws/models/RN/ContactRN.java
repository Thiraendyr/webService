package com.dtalavera.ejerciciosoa.crudaws.models.RN;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactRN {
	
	private Object id;
	
	private Name name;
	
	private Emails emails;

	public ContactRN() {
		// TODO Auto-generated constructor stub
	}
	
	public ContactRN(Object id, Name name, Emails emails) {
		super();
		this.id = id;
		this.name = name;
		this.emails = emails;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Emails getEmails() {
		return emails;
	}

	public void setEmails(Emails emails) {
		this.emails = emails;
	}

	@Override
	public String toString() {
		return "ContactRN [id=" + id + ", name=" + name + ", emails=" + emails + "]";
	}
	
	
	
}
