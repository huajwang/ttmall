package com.longmaple.oauth2.data;

public class Address {
	private String streetNo;
	private String postalCode;
	private String city;
	private String province;
	
	public Address(String streetNo, String postalCode, String city, String province) {
		super();
		this.streetNo = streetNo;
		this.postalCode = postalCode;
		this.city = city;
		this.province = province;
	}
	public String getStreetNo() {
		return streetNo;
	}
	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	
}
