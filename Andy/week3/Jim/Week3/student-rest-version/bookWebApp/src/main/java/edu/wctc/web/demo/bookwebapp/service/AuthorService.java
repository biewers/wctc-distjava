package edu.wctc.web.demo.bookwebapp.service;

import edu.wctc.web.demo.bookwebapp.entity.Author;
import edu.wctc.web.demo.bookwebapp.repository.AuthorRepository;
import edu.wctc.web.demo.bookwebapp.repository.BookRepository;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a special Spring-enabled service class that delegates work to 
 * various Spring managed repository objects using special spring annotations
 * to perform dependency injection, and special annotations for transactions.
 * It also uses SLF4j to provide logging features.
 * 
 * @author jlombardo
 */
@Repository("authorService")
@Transactional(readOnly = true)
public class AuthorService {

    private transient final Logger LOG = LoggerFactory.getLogger(AuthorService.class);

    @Inject
    private AuthorRepository authorRepo;

    @Inject
    private BookRepository bookRepo;

    public AuthorService() {
    }

    public List<Author> findAll() {
        LOG.debug("**** Calling findAll method");
        return authorRepo.findAll();
    }
    
    public Author findByIdAndFetchBooksEagerly(String id) {
        Integer authorId = new Integer(id);
        return authorRepo.findByIdAndFetchBooksEagerly(authorId);
    }

    public Author findById(String id) {
        return authorRepo.findOne(new Integer(id));
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void remove(Author author) {
        LOG.debug("Deleting author: " + author.getAuthorName());
        authorRepo.delete(author);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Author edit(Author author) {
        return authorRepo.saveAndFlush(author);
    }
}
