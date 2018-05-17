package edu.wctc.dj.week13.namesapp13.data.dao;

import edu.wctc.dj.week13.namesapp13.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAddressDAO extends JpaRepository<Address, String>
{
	
}
