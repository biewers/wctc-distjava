package booksample;

import java.util.List;

/**
 *
 * @author jlombardo
 */
public class AuthorService {
    private AuthorDaoStrategy dao;


    public AuthorService(AuthorDaoStrategy dao) {
        this.dao = dao;

    }
    
    public List<Author> getAllAuthors() throws Exception {
        return dao.getAllAuthors();
    }
    
    public static void main(String[] args) {
        
    }
}
