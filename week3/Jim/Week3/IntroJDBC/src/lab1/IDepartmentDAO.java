package lab1;

import db.accessor.DBAccessor;
import java.util.List;

/**
 * An example of a Strategy Pattern Interface that allows creation of 
 * different varieties of Department DAO implementations. For example, we
 * can create a 'Mock' implementation for testing, where no database
 * connection is needed.
 * 
 * @author jlombardo
 */
public interface IDepartmentDAO {

    /**
     * Retrieve all department records and package the data up as a collection of
     * entity objects.
     *
     * @return - collection of entity objects
     * @throws DataAccessException - if sql or I/O errors
     */
    List<Department> getAllDepartments() throws DataAccessException;

    DBAccessor getDb();

    void setDb(DBAccessor db);
    
}
