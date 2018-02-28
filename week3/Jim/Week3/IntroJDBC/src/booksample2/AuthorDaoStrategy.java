package booksample2;

import java.util.List;

/**
 *
 * @author jlombardo
 */
public interface AuthorDaoStrategy {

    List<Author> getAuthorList() throws Exception;
    
}
