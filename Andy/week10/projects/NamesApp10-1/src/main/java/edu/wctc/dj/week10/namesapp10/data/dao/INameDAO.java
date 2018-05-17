package edu.wctc.dj.week10.namesapp10.data.dao;

import edu.wctc.dj.week10.namesapp10.model.Name;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INameDAO extends JpaRepository<Name, String>{
	
	// TODO additional custom query methods as needed

}
