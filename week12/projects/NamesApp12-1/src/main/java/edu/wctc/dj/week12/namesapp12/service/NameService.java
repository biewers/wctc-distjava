package edu.wctc.dj.week12.namesapp12.service;

import edu.wctc.dj.week12.namesapp12.data.dao.INameDAO;
import edu.wctc.dj.week12.namesapp12.model.Name;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NameService {

	@Autowired
	private INameDAO nameDao;

	public Name getName(String id) {
		return nameDao.getOne(id);
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
