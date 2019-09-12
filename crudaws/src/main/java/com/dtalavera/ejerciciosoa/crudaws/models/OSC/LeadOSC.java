package com.dtalavera.ejerciciosoa.crudaws.models.OSC;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"Name",
	"ContactPartyNumber"
})
public class LeadOSC {
	@JsonProperty("Name")
	private String Name;
	@JsonProperty("ContactPartyNumber")
	private long ContactPartyNumber;
	
	public LeadOSC() {
		// TODO Auto-generated constructor stub
	}
	
	public LeadOSC(String Name, long ContactPartyNumber) {
		super();
		this.Name = Name;
		this.ContactPartyNumber = ContactPartyNumber;
	}
	
	@JsonProperty("Name")
	public String getName() {
		return Name;
	}
	@JsonProperty("Name")
	public void setName(String name) {
		this.Name = name;
	}
	
	@JsonProperty("ContactPartyNumber")
	public long getContactPartyNumber() {
		return ContactPartyNumber;
	}
	@JsonProperty("ContactPartyNumber")
	public void setContactPartyNumber(long ContactPartyNumber) {
		this.ContactPartyNumber = ContactPartyNumber;
	}

	@Override
	public String toString() {
		return "LeadOSC [Name=" + Name + ", ContactPartyNumber=" + ContactPartyNumber + "]";
	}
	
	
}
