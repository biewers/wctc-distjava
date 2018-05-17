package lab1;

/**
 * This class acts as a Data Transfer Object (DTO) -- which is a class that
 * represents information from multiple tables (multiple entities).
 * 
 * @author Instlogin
 */
public class EmployeeDeptDTO {
    private String lastName;
    private String firstName;
    private String deptName;

    public EmployeeDeptDTO() {
    }

    public EmployeeDeptDTO(String lastName, String firstName, String deptName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "EmployeeDeptDTO{" + "lastName=" + lastName + ", firstName=" + firstName + ", deptName=" + deptName + '}';
    }
    
    
}
