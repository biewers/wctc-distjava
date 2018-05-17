package edu.wctc.dj.week14.week14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dog implements Barkable {

	private static final Logger LOGGER = LoggerFactory.getLogger(Dog.class);

	private String firstName;
	private String lastName;

	public Dog(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@Override
	public void bark(int i) {
		LOGGER.info("The dog says woof " + i);
	}

}
