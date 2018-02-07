package edu.wctc.dj.week3.model;

import java.util.Arrays;
import java.util.List;

public class NameService {

	private static Name[] names = {
		new Name("fred", "Fred", "Smith"),
		new Name("joe", "Joe", "Jones")
	};
	
	public List<Name> getAllNames() {
		return Arrays.asList(names);
	}

	public Name getName(String id) {
		for (Name name : names) {
			if (name.getId().equals(id)) {
				return name;
			}
		}

		return null;
	}
}
