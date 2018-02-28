package edu.wctc.dj.week3.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NameService {

	private List<Name> nameList = Arrays.asList(
		new Name("1", "Fred", "Jones"),
		new Name("2", "Joe", "Smith")
	);

	public Name getName(String id) {
		Name theName = null;

		for (Name name : nameList) {
			if (name.getId().equals(id)) {
				theName = name;
			}
		}

		return theName;
	}

	public List<Name> getAllNames(){
		return nameList;
	}

	public List<Name> findNames(String search) {
		search = search.toLowerCase();
		List<Name> theList = new ArrayList<>();
		for (Name name : nameList) {
			if (name.getFirst().toLowerCase().startsWith(search) ||
			    name.getLast().toLowerCase().startsWith(search)) {
				theList.add(name);
			}
		}
		return theList;
	}
	
}
