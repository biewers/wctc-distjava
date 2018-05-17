package edu.wctc.dj.week10.namesapp10.data.dao;

import edu.wctc.dj.week10.namesapp10.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAddressDAO extends JpaRepository<Address, String>{
	
	// TODO additional custom query methods as needed

}
