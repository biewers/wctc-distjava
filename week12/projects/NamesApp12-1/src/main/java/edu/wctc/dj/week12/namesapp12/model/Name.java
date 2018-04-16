package edu.wctc.dj.week9.namesapp9.model;

public class Name {

	private String id;
	private String first;
	private String last;
	private Address address;

	public Name(String id, String first, String last, Address address) {
		this.id = id;
		this.first = first;
		this.last = last;
		this.address = address;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
