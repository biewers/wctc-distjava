package edu.wctc.dj.week12.namesapp12.data.dao;

import edu.wctc.dj.week12.namesapp12.model.Name;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INameDAO extends JpaRepository<Name, String>
{
	
}
