package lab1;

import db.accessor.DBAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * An example of a Mock Data Access Object (DAO) for Employee information. The
 * purpose of this class is to provide an implementation for testing
 * purposes that doesn't access the database. Rather when unit testing you
 * NEVER access external resources. Instead, we fake this access,
 * which makes testing easier and faster because the db connection is not
 * used or needed.
 * 
 * @author Jim Lombardo
 */
public class MockEmployeeDAO implements IEmployeeDAO {
    private DBAccessor db; // not used or need, but required due to getter/setter

    public MockEmployeeDAO() {
    }
    
    @Override
    public List<EmployeeDeptDTO> getEmployeeByDeptId(String id) throws DataAccessException {
        List<EmployeeDeptDTO> records = new ArrayList<EmployeeDeptDTO>();
        
        if(id.equals("1")) {
            EmployeeDeptDTO mockDto = 
                    new EmployeeDeptDTO("Jones", "Bob", "Marketing");
            records.add(mockDto);
        }
        
        return records;
    }
    
    @Override
    public List<Employee> getAllEmployees() throws DataAccessException {
        List<Employee> records = new ArrayList<Employee>();
        Employee emp = new Employee(1L, "Smith", "Dave", "dsmith@isp.com", new Date(), 1);
        records.add(emp);
        
        return records;
    }

    //================================================================
    // These methods are unfinished for this demo, which is why they throw
    //  UnsupportedOperationException 
    //=================================================================
    @Override
    public void save(Employee emp) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteEmployee(Employee employee) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Employee findEmployeeById(String id) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DBAccessor getDb() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDb(DBAccessor db) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
    // Test harness only -- remove in production version
    public static void main(String[] args) throws Exception {
        MockEmployeeDAO dao = new MockEmployeeDAO();

        List<EmployeeDeptDTO> records = 
                dao.getEmployeeByDeptId("1");

        for(EmployeeDeptDTO record : records) {
            System.out.print(record.getDeptName() + ", ");
            System.out.print(record.getFirstName() + " ");
            System.out.println(record.getLastName());
        }

    }
    
}
