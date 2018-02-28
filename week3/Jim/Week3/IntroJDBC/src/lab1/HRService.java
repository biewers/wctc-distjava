package lab1;

import db.accessor.DBAccessor;
import db.accessor.MySqlDbAccessor;
import java.util.List;

/**
 * This is a service class (an implementation of the Facade Design Pattern) 
 * for various Human Resource related tasks. It is a high-level class and 
 * the main connection point to a data store from a program. Notice how HR
 * tasks span both employee and department data, and notice how the method
 * names use problem domain terminology.
 * 
 * @author Instlogin
 */
public class HRService {
    private IEmployeeDAO empDao;
    private IDepartmentDAO deptDao;
    
    public HRService() {
        // if we were using Spring, we could inject these, removing the
        // dependency on these concrete classes!
        DBAccessor db = new MySqlDbAccessor();
        deptDao = new DepartmentDAO(db);
        empDao = new EmployeeDAO(db);
        // Just use this implementation for Unit Testing...
//        empDao = new MockEmployeeDAO();
    }
    
    public List<Employee> getAllEmployees() throws DataAccessException {
        return empDao.getAllEmployees();
    }
    
    public List<Department> getAllDepartments() throws DataAccessException {
        return deptDao.getAllDepartments();
    }
    
    public List<EmployeeDeptDTO> getEmployeesByDeptId(String deptId) throws DataAccessException {
        return empDao.getEmployeeByDeptId(deptId);
    }
    
    
    // Test harness only -- remove in production version
    public static void main(String[] args) throws DataAccessException {
        HRService hr = new HRService();
        
        System.out.println("Getting all departments...\n");
        System.out.println(hr.getAllDepartments());
        
        System.out.println("\nGetting all employees...\n");
        System.out.println(hr.getAllEmployees());
        
        System.out.println("\nGetting employees by department...\n");
        System.out.println(hr.getEmployeesByDeptId("1"));
    }
    
}
