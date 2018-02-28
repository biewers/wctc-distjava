package edu.wctc.web.demo.bookwebapp.repository;

import edu.wctc.web.demo.bookwebapp.entity.Author;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author jlombardo
 */
@RepositoryRestResource(collectionResourceRel = "authors", path = "authors")
public interface AuthorRepository extends JpaRepository<Author, Integer>, Serializable {
    
    @Query("SELECT a FROM Author a JOIN FETCH a.bookSet WHERE a.authorId = (:id)")
    public Author findByIdAndFetchBooksEagerly(@Param("id") Integer id);
//    
    @Query("SELECT a.authorName FROM Author a")
    public Object[] findAllWithNameOnly();
}
