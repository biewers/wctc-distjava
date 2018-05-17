package edu.wctc.web.demo.bookwebapp.model;

import java.util.Date;
import java.util.Objects;

/**
 * This is a domain object, also called an "entity" object. 
 * 
 * @author jlombardo
 */
public class Author {
    private final String NULL_MSG = " cannot be null";
    
    private Integer authorId;
    private String authorName;
    private Date dateAdded;

    public Author() {
    }

    public Author(Integer authorId) {
        this.authorId = authorId;
    }

    public Author(Integer authorId, String authorName, Date dateAdded) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.dateAdded = dateAdded;
    }

    public final Integer getAuthorId() {
        return authorId;
    }

    public final void setAuthorId(Integer authorId) throws IllegalArgumentException {
        if(authorId == null) {
            throw new IllegalArgumentException("author id" + NULL_MSG);
        }
        this.authorId = authorId;
    }

    public final String getAuthorName() {
        return authorName;
    }

    public final void setAuthorName(String authorName) throws IllegalArgumentException {
        if(authorName == null || authorName.isEmpty()) {
            throw new IllegalArgumentException("author name is required");
        }
        this.authorName = authorName;
    }

    public final Date getDateAdded() {
        return dateAdded;
    }

    public final void setDateAdded(Date dateAdded) throws IllegalArgumentException {
        if(dateAdded == null) {
            throw new IllegalArgumentException("date addedl" + NULL_MSG);
        }
         this.dateAdded = dateAdded;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.authorId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (!Objects.equals(this.authorId, other.authorId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Author{" + "authorId=" + authorId + ", authorName=" + authorName + ", dateAdded=" + dateAdded + '}';
    }
    
    
}
