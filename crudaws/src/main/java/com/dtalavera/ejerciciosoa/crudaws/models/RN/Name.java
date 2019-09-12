package com.dtalavera.ejerciciosoa.crudaws.models.RN;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Name {

	private String first;
	
	private String last;

	public Name() {
		// TODO Auto-generated constructor stub
	}
	public Name(String first, String last) {
		super();
		this.first = first;
		this.last = last;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}
	@Override
	public String toString() {
		return "Name [first=" + first + ", last=" + last + "]";
	}
	
	
}
