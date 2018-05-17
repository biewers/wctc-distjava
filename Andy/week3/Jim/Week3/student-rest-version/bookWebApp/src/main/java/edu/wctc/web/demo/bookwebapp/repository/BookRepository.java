package edu.wctc.web.demo.bookwebapp.repository;

import edu.wctc.web.demo.bookwebapp.entity.Book;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author jlombardo
 */
@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends JpaRepository<Book, Integer>, Serializable {
    
}
