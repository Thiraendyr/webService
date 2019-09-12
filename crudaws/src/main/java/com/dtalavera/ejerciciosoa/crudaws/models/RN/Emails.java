package com.dtalavera.ejerciciosoa.crudaws.models.RN;

public class Emails {
	
	private String address;
	
	private AddressType addressType;
	
	public Emails() {
		// TODO Auto-generated constructor stub
	}

	public Emails(String address, AddressType addressType) {
		super();
		this.address = address;
		this.addressType = addressType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public void AddressTypeRN(AddressType addressType) {
		this.addressType = addressType;
	}

	@Override
	public String toString() {
		return "Emails [address=" + address + ", addressType=" + addressType + "]";
	}
	
	
	
	
}
