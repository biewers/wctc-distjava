package edu.wctc.jgl.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
    
    public List<Author> getAuthorList() 
            throws ClassNotFoundException, SQLException {
        
        return dao.getAuthorList();
    }
    
    public static void main(String[] args) throws Exception {
        AuthorDaoStrategy dao = new AuthorDao(
            new MySqlDbStrategy(),
            "com.mysql.jdbc.Driver",
            "jdbc:mysql://localhost:3306/book?useSSL=false",
            "root", "admin");
        AuthorService service = new AuthorService(dao);
        List<Author> authors = service.getAuthorList();
        System.out.println(authors);
    }
}
