package booksample2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jlombardo
 */
public class MySqlDbStrategy implements DBStrategy {
    private static final boolean DEBUG = true;
    private Connection conn;

    @Override
    public void openConnection(String driverClass, String url,
            String userName, String password) throws Exception {
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, userName, password);
    }
    
    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }
    
    @Override
    public List<Map<String,Object>> findAllRecords(String tableName, int recLimit) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " LIMIT " + recLimit;
        List<Map<String,Object>> recordList = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        while(rs.next()) {
            Map<String,Object> record = new HashMap<>();
            for(int i=1; i <= columnCount; i++) {
                record.put(metaData.getColumnName(i),rs.getObject(i));
            }
            recordList.add(record);
        }
        
        return recordList;
    }
    
    @Override
    public void deleteById(String tableName, String primaryKeyFieldName, 
            Object primaryKeyValue) throws SQLException {
        
        String sql = "DELETE FROM " + tableName + " WHERE " + primaryKeyFieldName + " = ";
        if(primaryKeyValue instanceof String) {
            // add single quotes around value
            sql += "'" + primaryKeyValue.toString() + "'";
        } else {
            // must be a number, don't surround value with single quotes
            sql += primaryKeyValue.toString();
        }
        if(DEBUG) System.out.println(sql);
        
        Statement stmt = conn.createStatement();
        // recordsDeleted count not used, but could be later
        stmt.executeUpdate(sql);
    }
    
    public void psDeleteById(String tableName, String primaryKeyFieldName, 
            Object primaryKeyValue) throws SQLException {
        
        String sql = "DELETE FROM " + tableName 
                + " WHERE " + primaryKeyFieldName + " = ?";
        
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setObject(1, primaryKeyValue);
        // recordsDeleted count not used, but could be later
        stmt.executeUpdate();
    }
    
    public static void main(String[] args) throws Exception {
        MySqlDbStrategy db = new MySqlDbStrategy();
        
        // ALWAYS OPEN THE CONNECTION BEFORE YOU RUN YOUR QUERY
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        
        System.out.println("All author records before delete:");
        List<Map<String,Object>> records =
                db.findAllRecords("author",50);
        for(Map record : records) {
            System.out.println(record);
        }
        
        db.deleteById("author", "author_id", 2);
        
        System.out.println("\nAll author records before delete:");
        records =
                db.findAllRecords("author",50);
        for(Map record : records) {
            System.out.println(record);
        }
        
        // DON'T FORGET TO CLOSE THE CONNECTION WHEN YOU ARE DONE!!!
        db.closeConnection();
    }
}
