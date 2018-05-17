package booksample;

import java.util.List;

/**
 *
 * @author jlombardo
 */
public interface AuthorDaoStrategy {

    List<Author> getAllAuthors() throws Exception;
    
}
