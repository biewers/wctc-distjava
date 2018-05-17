package lab1;

import db.accessor.DBAccessor;
import db.accessor.MySqlDbAccessor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An example of a Data Access Object (DAO) for Employee information. Notice how
 * the method names use problem domain terminology as opposed to more technical,
 * db-realted terminology. For example, we say "getEmployeeByDeptId" vs.
 * "getAllRecordsByForeginKey".
 *
 * @author Instlogin
 */
public class EmployeeDAO implements IEmployeeDAO {

    private static final String FIND_ALL_EMPLOYEES =
            "SELECT * from EMPLOYEE";
    private static final String EMP_BY_DEPT_ID =
            "SELECT e.lastname, e.firstname, d.dept_name "
            + "FROM Employee e, Department d "
            + "WHERE e.dept_id = d.dept_id and d.dept_id = ";
    private DBAccessor db;

    /**
     * default constructor requires DBAccess to be set separately
     */
    public EmployeeDAO() {
    }

    /**
     * Convenience constructor sets DBAccessor automatically
     *
     * @param db - the desired implementation
     */
    public EmployeeDAO(DBAccessor db) {
        this.db = db;
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
                    "jdbc:mysql://bit.glassfish.wctc.edu:3306/jsfshowcase",
                    "advjava", "advjava");
        } catch (IllegalArgumentException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
    }

    /**
     * Saves an Employee as a new or updated record.
     *
     * @param emp - the entity to be saved or updated
     * @throws DataAccessException - if sql or I/O errors
     */
    @Override
    public void save(Employee emp) throws DataAccessException {
        this.openLocalDbConnection();
        
        String tableName = "EMPLOYEE";
        List<String> fieldNames =
                new ArrayList<String>();
        fieldNames.add("LASTNAME");
        fieldNames.add("FIRSTNAME");
        fieldNames.add("EMAIL");
        fieldNames.add("dept_id");
        fieldNames.add("HIREDATE");

        List fieldValues =
                new ArrayList();
        fieldValues.add(emp.getLastName());
        fieldValues.add(emp.getFirstName());
        fieldValues.add(emp.getEmail());
        fieldValues.add(emp.getDeptId());
        fieldValues.add(emp.getHireDate());

        try {
            // if the id is null, it's a new record, else it's an update
            if (emp.getId() == null) {
                db.insertRecord(
                        tableName, fieldNames,
                        fieldValues, true);
            } else {
                db.updateRecords(
                        tableName, fieldNames,
                        fieldValues, "ID", emp.getId(), true);
            }
        } catch (SQLException e1) {
            throw new DataAccessException(e1.getMessage(), e1);

        } catch (Exception e2) {
            throw new DataAccessException(e2.getMessage(), e2);
        }
    }

    /**
     * Delete an employee by entity.
     *
     * @param employee - the entity to be deleted.
     * @throws DataAccessException - if sql or I/O errors
     */
    @Override
    public void deleteEmployee(Employee employee) throws DataAccessException {
        this.openLocalDbConnection();
        
        try {
            db.deleteRecords("Employee", "ID", employee.getId(), true);
        } catch (SQLException e1) {
            throw new DataAccessException(e1.getMessage(), e1);

        } catch (Exception e2) {
            throw new DataAccessException(e2.getMessage(), e2);
        }
    }

    /**
     * Retrieve all employee records and package the data up as a collection of
     * entity objects.
     *
     * @return - collection of entity objects
     * @throws DataAccessException - if sql or I/O errors
     */
    @Override
    public List<Employee> getAllEmployees() throws DataAccessException {
        openLocalDbConnection();
//        openCampusDbConnection();        
        List<Map> rawData = new ArrayList<Map>();
        List<Employee> records = new ArrayList<Employee>();
        try {
            rawData = db.findRecords(FIND_ALL_EMPLOYEES, true);
        } catch (SQLException e1) {
            throw new DataAccessException(e1.getMessage(), e1);

        } catch (Exception e2) {
            throw new DataAccessException(e2.getMessage(), e2);
        }

        Employee employee = null;

        // Translate List<Map> into List<Employee>
        for (Map m : rawData) {
            employee = new Employee();

            String id = m.get("ID").toString();
            employee.setId(new Long(id));
            String firstName = m.get("FIRSTNAME").toString();
            employee.setFirstName(firstName);
            String lastName = m.get("LASTNAME").toString();
            employee.setLastName(lastName);
            String email = m.get("EMAIL").toString();
            employee.setEmail(email);
            Date hireDate = (Date) m.get("HIREDATE");
            employee.setHireDate(hireDate);
            String deptId = m.get("dept_id").toString();
            employee.setDeptId(new Integer(deptId));

            records.add(employee);
        }

        return records;
    }

    /**
     * This method retrieves employee and department data using an sql join.
     * Because the data returned is combined from two database tables we cannot
     * convert the data directly into employee or department entities. Instead
     * we will use a custom "DTO (Data Transfer Object)" class whose job it is
     * to consolidate the data from the two tables.
     *
     * @param id - the department id
     * @return a DTO containing the consolidated data from the join query
     * @throws DataAccessException - if sql or I/O errors
     */
    @Override
    public List<EmployeeDeptDTO> getEmployeeByDeptId(String id) throws DataAccessException {
        this.openLocalDbConnection();
        
        List<Map> rawData = new ArrayList<Map>();
        List<EmployeeDeptDTO> records = new ArrayList<EmployeeDeptDTO>();
        try {
            rawData = db.findRecords(EMP_BY_DEPT_ID + id, true);
        } catch (SQLException e1) {
            throw new DataAccessException(e1.getMessage(), e1);

        } catch (Exception e2) {
            throw new DataAccessException(e2.getMessage(), e2);
        }

        EmployeeDeptDTO dto = null;

        // Translate List<Map> into List<Employee>
        for (Map m : rawData) {
            dto = new EmployeeDeptDTO();
            String firstName = m.get("FIRSTNAME").toString();
            dto.setFirstName(firstName);
            String lastName = m.get("LASTNAME").toString();
            dto.setLastName(lastName);
            String deptName = m.get("dept_name").toString();
            dto.setDeptName(deptName);
            records.add(dto);
        }

        return records;
    }

    /**
     * Finds an employee entity by its id.
     *
     * @param id - the primary key
     * @return the entity object
     * @throws DataAccessException - if sql or I/O errors
     */
    @Override
    public Employee findEmployeeById(String id) throws DataAccessException {
        this.openLocalDbConnection();
        
        Map rec;
        try {
            rec = db.getRecordByID("EMPLOYEE", "ID", new Long(id), true);
        } catch (SQLException e1) {
            throw new DataAccessException(e1.getMessage(), e1);

        } catch (Exception e2) {
            throw new DataAccessException(e2.getMessage(), e2);
        }

        Employee employee = new Employee();
        employee.setId(new Long(rec.get("ID").toString()));
        employee.setFirstName(rec.get("FIRSTNAME").toString());
        employee.setLastName(rec.get("LASTNAME").toString());
        employee.setEmail(rec.get("EMAIL").toString());
        Date hireDate = (Date) rec.get("HIREDATE");
        employee.setHireDate(hireDate);
        Integer deptId = (Integer) rec.get("dept_id");
        employee.setDeptId(deptId);

        return employee;
    }

    @Override
    public DBAccessor getDb() {
        return this.db;
    }

    @Override
    public void setDb(DBAccessor db) {
        this.db = db;
    }

    // Test harness only -- remove in production version
    public static void main(String[] args) throws Exception {
        EmployeeDAO dao = new EmployeeDAO(new MySqlDbAccessor());

        // Test get all employees...
        List<Employee> records = dao.getAllEmployees();

        System.out.println("Found employee records...\n");
        for (Employee emp : records) {
            System.out.println(emp);
        }

        // Each time you perform a new query you must re-open the connection
//        dao.openLocalDbConnection();

        // Test get employees by department ...
//        List<EmployeeDeptDTO> data = dao.getEmployeeByDeptId("1");
//
//        System.out.println("\nFound employees by department...\n");
//        for (EmployeeDeptDTO rec : data) {
//            System.out.println(rec);
//        }

        // Create a new record
        // Each time you perform a new query you must re-open the connection
//        dao.openLocalDbConnection();
//        System.out.println("\nCreating new record...\n");
//        Employee emp = new Employee("Smith", "John");
//        emp.setDeptId(2);
//        emp.setEmail("jsmith@isp.com");
//        emp.setHireDate(new Date());
//        dao.save(emp);
//         

        // Update the record
        // Each time you perform a new query you must re-open the connection
//        dao.openLocalDbConnection();
//        System.out.println("\nUpdating the record...\n");
//        // first get the record to update
//        Employee empUpdatable = dao.findEmployeeById("11");
//        if (empUpdatable != null) {
//            empUpdatable.setDeptId(3); // change department
//            dao.save(empUpdatable);
//        } else {
//            System.out.println("Could not find employee id=11 to update");
//        }

//        // Delete the record
//        System.out.println("\nDeleting the record...\n");
//        // first get the record to update
//        Employee empDeletable = dao.findEmployeeById("11");
//        dao.deleteEmployee(empDeletable);



    }
}
