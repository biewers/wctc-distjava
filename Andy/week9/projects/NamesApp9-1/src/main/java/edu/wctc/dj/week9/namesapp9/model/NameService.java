package edu.wctc.dj.week9.namesapp9.model;

import edu.wctc.dj.week9.namesapp9.data.AddressDao;
import edu.wctc.dj.week9.namesapp9.data.NameDao;
import java.util.ArrayList;
import java.util.List;

public class NameService {

	public Name getName(String id) {
		Name theName = null;

		// TODO

		return theName;
	}

	public List<Name> getAllNames() throws Exception {
		NameDao nameDao = new NameDao();
		List<Name> nameList = nameDao.getNames();

		for (Name name : nameList) {
			AddressDao addrDao = new AddressDao();
			name.setAddress(addrDao.getAddress(name));
		}

		return nameList;
	}

	public List<Name> findNames(String search) {
		search = search.toLowerCase();
		List<Name> theList = new ArrayList<>();

		// TODO

		return theList;
	}

}
