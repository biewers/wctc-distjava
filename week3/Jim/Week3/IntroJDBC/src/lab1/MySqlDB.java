package lab1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Instlogin
 */
public class MySqlDB {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    
    public void openConnection(String driverClass, String url, String user, String password) {
        try {
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            
        } catch (SQLException ex) {
            
        }
    }
    
    public List<Map<String,Object>> getAllRecords(String table) throws SQLException {
        List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
        
        String sql = "SELECT * FROM " + table;
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        while( rs.next() ) {
            
        }
        return results;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }
    
    
}
