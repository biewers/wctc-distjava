package lab1;

import java.util.Set;

/**
 * An example of an 'Entity' class. An entity class is a representation of
 * a database table, where the class name is similar to the table name and 
 * the class properties are similar to the table columns (fields). You would
 * use this to provide structured data to/from database operation code.
 * 
 * @author jlombardo
 */
public class Department {
    private Long deptId;
    private String deptName;
    private Set<Employee> employees;

    public Department() {
    }

    public Department(String deptName) {
        this.deptName = deptName;
    }

    public Department(Long deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Department other = (Department) obj;
        if (this.deptId != other.deptId && (this.deptId == null || !this.deptId.equals(other.deptId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.deptId != null ? this.deptId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Department{" + "deptId=" + deptId + ", deptName=" + deptName + '}';
    }
    
    
}
