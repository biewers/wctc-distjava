package booksample2;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jlombardo
 */
public class MockAuthorDao implements AuthorDaoStrategy {
    
    @Override
    public List<Author> getAuthorList() throws Exception {
        List<Author> records = Arrays.asList(
                new Author(1,"Mark Twain",new Date()),
                new Author(2,"Stephen King",new Date()),
                new Author(3,"George Orwell",new Date())
        );
        
        return records;
        
    }
    
    public static void main(String[] args) throws Exception {
        MockAuthorDao dao = new MockAuthorDao();
    
        List<Author> authors = dao.getAuthorList();
        for(Author a : authors) {
            System.out.println(a);
        }
    }
}
