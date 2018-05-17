package lab1;

import db.accessor.DBAccessor;
import db.accessor.MySqlDbAccessor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An example of a Data Access Object (DAO) for Department information. Notice 
 * how the method names use problem domain terminology as opposed to more 
 * technical, db-realted terminology. For example, we say "getAllDepartments" 
 * vs. "getAllRecords".
 * 
 * @author Instlogin
 */
public class DepartmentDAO implements IDepartmentDAO {
    private static final String FIND_ALL_DEPT =
            "SELECT * FROM department";
    private DBAccessor db;

    /** default constructor requires DBAcessor be set separately */
    public DepartmentDAO() {
    }

    /**
     * Convenience constructor sets DBAccessor automatically
     * @param db 
     */
    public DepartmentDAO(DBAccessor db) {
        this.db = db;
    }

    /**
     * Retrieve all department records and package the data up as a collection of
     * entity objects.
     *
     * @return - collection of entity objects
     * @throws DataAccessException - if sql or I/O errors
     */
     @Override
    public List<Department> getAllDepartments() throws DataAccessException {
        this.openLocalDbConnection();
        
        List<Map> rawData = new ArrayList<Map>();
        List<Department> records = new ArrayList<Department>();
        
        try {
            rawData = db.findRecords(FIND_ALL_DEPT, true);
        } catch (SQLException e1) {
            throw new DataAccessException(e1.getMessage(), e1);

        } catch (Exception e2) {
            throw new DataAccessException(e2.getMessage(), e2);
        }
        
        Department dept = null;
        
        // Translate List<Map> into List<Employee>
        for(Map m : rawData) {
            dept = new Department();
            String dept_id = m.get("dept_id").toString();
            dept.setDeptId(Long.parseLong(dept_id));
            String deptName = m.get("dept_name").toString();
            dept.setDeptName(deptName);
            
            records.add(dept);
        }
        
        return records;
    }
 
    /*
     * Not part of Interface for strategy. Just used here to show how
     * we can connect to local or remote databases.
     */
    private void openLocalDbConnection() throws DataAccessException {
        try {
            // Each time you perform a new query you must re-open the connection
            db.openConnection(
                    "com.mysql.jdbc.Driver",
                    "jdbc:mysql://localhost:3306/jsfshowcase",
                    "root", "admin");
        } catch (IllegalArgumentException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
    }

    /*
     * Not part of Interface for strategy. Just used here to show how
     * we can connect to local or remote databases.
     */
     private void openCampusDbConnection() throws DataAccessException {
        try {
            // Each time you perform a new query you must re-open the connection
            db.openConnection(
                    "com.mysql.jdbc.Driver",
                    "jdbc:mysql://localhost:3306/jsfshowcase",
                    "advjava", "advjava");
        } catch (IllegalArgumentException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
    }   

    @Override
    public DBAccessor getDb() {
        return db;
    }

    @Override
    public void setDb(DBAccessor db) {
        this.db = db;
    }
    
    public static void main(String[] args) throws DataAccessException {
        DepartmentDAO dao = new DepartmentDAO(new MySqlDbAccessor());

        // My local server
        dao.openLocalDbConnection();

        // Test get all employees...
        List<Department> records = dao.getAllDepartments();

        System.out.println("Found department records...\n");
        for (Department d : records) {
            System.out.println(d);
        }
        
    }
}
