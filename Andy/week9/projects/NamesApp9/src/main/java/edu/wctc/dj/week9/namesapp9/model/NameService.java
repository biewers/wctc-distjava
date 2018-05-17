package edu.wctc.dj.week9.namesapp9.model;

import edu.wctc.dj.week9.namesapp9.data.AddressDAO;
import edu.wctc.dj.week9.namesapp9.data.NameDAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NameService {

	public Name getName(String id) {
		// TODO
		return null;
	}

	public List<Name> getAllNames() throws Exception {
		NameDAO nameDao = new NameDAO();
		List<Name> nameList = nameDao.getNames();

		if (nameList != null) {
			AddressDAO addressDao = new AddressDAO();
			for (Name name : nameList) {
				name.setAddress(addressDao.getAddress(name));
			}
		}

		return nameList;
	}

	public List<Name> findNames(String search) {
		// TODO
		return null;
	}
	
}
