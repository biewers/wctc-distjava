package edu.wctc.dj.week12.namesapp12.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Name {

	@Id
	@GeneratedValue
	private String id;

	@Column(name = "firstname")
	private String first;

	@Column(name = "lastname")
	private String last;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "addrid")
	private Address address;

	public Name() {
	}

	public Name(String first, String last) {
		this.first = first;
		this.last = last;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
