package edu.wctc.dj.week14.namesapp14.data.dao;

import edu.wctc.dj.week14.namesapp14.model.Name;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INameDAO extends JpaRepository<Name, String>
{
	
}
