package edu.wctc.jgl.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jlombardo
 */
public class AuthorDao implements AuthorDaoStrategy {
    private DbStrategy db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;

    public AuthorDao(DbStrategy db, String driverClass, String url, String userName, String password) {
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }
    
    @Override
    public List<Author> getAuthorList() 
            throws ClassNotFoundException, SQLException {
        
        db.openConnection(driverClass, url, userName, password);
        
        List<Map<String,Object>> records = db.findAllRecords("author", 500);
        List<Author> authors = new ArrayList<>();
        for(Map<String,Object> rec : records) {
            Author author = new Author();
            Integer id = Integer.parseInt(rec.get("author_id").toString());
            author.setAuthorId(id);
            String name = rec.get("author_name").toString();
            author.setAuthorName(name != null ? name : "");
            Date date = (Date)rec.get("date_added");
            author.setDateAdded(date);
            authors.add(author);
        }
        
        db.closeConnection();
        return authors;
    }

    public DbStrategy getDb() {
        return db;
    }

    public void setDb(DbStrategy db) {
        this.db = db;
    }
    
//    public static void main(String[] args) throws Exception {
//        AuthorDaoStrategy dao = new AuthorDao(
//            new MySqlDbStrategy(),
//            "com.mysql.jdbc.Driver",
//            "jdbc:mysql://localhost:3306/book?useSSL=false",
//            "root", "admin");
//        
//        List<Author> authors = dao.getAuthorList();
//        System.out.println(authors);
//    }
}
