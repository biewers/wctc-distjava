package edu.wctc.dj.week14.namesapp14.rest;

import edu.wctc.dj.week14.namesapp14.model.Name;
import edu.wctc.dj.week14.namesapp14.service.NameService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NameController {

	@Autowired
	private NameService nameService;

	@RequestMapping(method = POST, path = "/name")
	public Name createName(@RequestBody Name name) {
		return nameService.createName(name);
	}

	// Update (i.e., PUT)
	@RequestMapping(method = PUT, path = "/name")
	public Name updateName(@RequestBody Name name) {
		return nameService.updateName(name);
	}

	@RequestMapping(method = GET, path = "/name")
	public List<Name> getNameList() throws Exception {
		return nameService.getAllNames();
	}
	
	@RequestMapping(method = GET, path = "/name/{id}")
	public Name getName(@PathVariable String id) {
		Name name = nameService.getName(id);
		return name;
	}

	@RequestMapping(method = DELETE, path = "/name/{id}")
	public void deleteName(@PathVariable String id) {
		nameService.deleteName(id);
	}

}
