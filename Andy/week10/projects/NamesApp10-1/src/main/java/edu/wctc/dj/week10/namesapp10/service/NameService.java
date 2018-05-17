package edu.wctc.dj.week10.namesapp10.service;

import edu.wctc.dj.week10.namesapp10.data.dao.INameDAO;
import edu.wctc.dj.week10.namesapp10.model.Name;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;

@Service
@Transactional
public class NameService {

	@Autowired
	private INameDAO nameDao;

	public Name getName(String id) {
		return nameDao.findOne(id);
	}

	public List<Name> getAllNames() throws Exception {
		return nameDao.findAll();
	}

	public List<Name> findNames(String search) {
		ExampleMatcher matcher = ExampleMatcher.matching()
			.withMatcher("last", startsWith().ignoreCase());
		Name name = new Name();
		name.setLast(search);
		return nameDao.findAll(Example.of(name, matcher));
	}
	
}
