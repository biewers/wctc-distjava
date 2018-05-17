package booksample2;

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
    
    public List<Author> getAuthorList() throws Exception {
        return dao.getAuthorList();
    }
    
    public static void main(String[] args) throws Exception {
        AuthorDao dao = new AuthorDao(new MySqlDbStrategy(),"com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");

        // Or, for unit testing...
//        MockAuthorDao dao = new MockAuthorDao();
        
        AuthorService authorService = new AuthorService(dao);
        
        List<Author> authors = authorService.getAuthorList();
        for(Author a : authors) {
            System.out.println(a);
        }
        
    }
}
