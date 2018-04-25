package edu.wctc.dj.week13.namesapp13.data.dao;

import edu.wctc.dj.week13.namesapp13.model.Name;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INameDAO extends JpaRepository<Name, String>
{
	
}
