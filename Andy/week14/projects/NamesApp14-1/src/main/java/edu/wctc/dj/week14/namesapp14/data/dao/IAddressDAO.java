package edu.wctc.dj.week14.namesapp14.data.dao;

import edu.wctc.dj.week14.namesapp14.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAddressDAO extends JpaRepository<Address, String>
{
	
}
