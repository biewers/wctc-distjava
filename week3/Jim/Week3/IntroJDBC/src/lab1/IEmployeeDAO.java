package lab1;

import db.accessor.DBAccessor;
import java.util.List;

/**
 * An example of a Strategy Pattern Interface that allows creation of 
 * different varieties of Employee DAO implementations. For example, we
 * can create a 'Mock' implementation for testing, where no database
 * connection is needed.
 * 
 * @author jlombardo
 */
public interface IEmployeeDAO {
    
    /**
     * Saves an Employee as a new or updated record.
     * @param emp - the entity to be saved or updated
     * @throws DataAccessException - if sql or I/O errors
     */
    public abstract void save(Employee emp) throws DataAccessException;
    
    /**
     * Delete an employee by entity.
     * @param employee - the entity to be deleted.
     * @throws DataAccessException  - if sql or I/O errors
     */
    public abstract void deleteEmployee(Employee employee) throws DataAccessException;
    
    /**
     * Finds an employee entity by its id.
     * @param id - the primary key
     * @return the entity object
     * @throws DataAccessException - if sql or I/O errors
     */
    public Employee findEmployeeById(String id) throws DataAccessException;
    
    /**
     * Retrieve all employee records and package the data up as a
     * collection of entity objects.
     * 
     * @return - collection of entity objects
     * @throws DataAccessException - if sql or I/O errors
     */
    public abstract List<Employee> getAllEmployees() throws DataAccessException;

    public abstract DBAccessor getDb();
    
    /**
     * This method retrieves employee and department data using an sql
     * join. Because the data returned is combined from two database tables
     * we cannot convert the data directly into employee or department
     * entities. Instead we will use a custom "DTO (Data Transfer Object)" class
     * whose job it is to consolidate the data from the two tables.
     * 
     * @param id - the department id
     * @return a DTO containing the consolidated data from the join query
     * @throws DataAccessException - if sql or I/O errors
     */
    public abstract List<EmployeeDeptDTO> getEmployeeByDeptId(String id) throws DataAccessException;

    public abstract void setDb(DBAccessor db);
    
}
