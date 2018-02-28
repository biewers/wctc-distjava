package edu.wctc.jgl.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 *
 * @author jlombardo
 */
public class MySqlDbStrategy implements DbStrategy {

    private Connection conn;

    /**
     * A utility method to expicitly open a db connection. Note that the
     * connection will remain open until explicitly closed by member methods.
     *
     * @param driverClassName - the fully qualified name of the driver class.
     * @param url - the connection URL, driver dependent.
     * @param username for database access permission, if required. Null and ""
     * values are allowed.
     * @param password for database access persmission, if required. Null and ""
     * values are allowed
     * @throws IllegalArgumentException if url is null or zero length
     * @throws ClassNotFoundException if driver class cannot be found
     * @throws SQLException if database access error occurs. For example, an
     * invalid url could cause this; or, a database that is no longer available
     * due to network or access permission problems.
     */
    public void openConnection(String driverClassName, String url, String username, String password)
            throws IllegalArgumentException, ClassNotFoundException, SQLException {
        String msg = "Error: url is null or zero length!";
        if (url == null || url.length() == 0) {
            throw new IllegalArgumentException(msg);
        }
        username = (username == null) ? "" : username;
        password = (password == null) ? "" : password;
        Class.forName(driverClassName);
        conn = DriverManager.getConnection(url, username, password);
    }

    /**
     * A utility method to expicitly close a db connection. Pooled connections
     * should never be closed, but rather returned to the pool.
     * <p>
     * As an alternative to using this method, other member methods is this
     * class offer a boolean switch to close the connection automatically.
     *
     * @throws SQLException if connection cannot be closed due to a db access
     * error.
     */
    public void closeConnection() throws SQLException {
        conn.close();
    }

    /**
     * A general purpose record-retrieval method for a single table. The number
     * of records returned is controllable, but no record paging is provided.
     *
     * @param tableName - the name of the db table to be queried
     * @param maxRecords - the maximum records to be retrieved
     * @return a generic list of maps represented a collecton of records
     * @throws SQLException if something goes wrong
     */
    @Override
    public List<Map<String, Object>> findAllRecords(String tableName, int maxRecords)
            throws SQLException {

        String sql = "SELECT * FROM " + tableName + " LIMIT " + maxRecords;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Map<String, Object>> records = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();

        while (rs.next()) {
            Map<String, Object> record = new LinkedHashMap<>();
            for (int i = 0; i < colCount; i++) {
                String colName = rsmd.getColumnName(i + 1);
                Object colData = rs.getObject(colName);
                record.put(colName, colData);
            }
            records.add(record);
        }

        return records;
    }

    /**
     * Can be used to perform general purpose sql queries. This is especially
     * useful when other methods in the public interface don't provide the
     * needed features (such as complex, multiple criteria statements).
     *
     * @param sqlString - the sql statement (check your database for
     * compatibility)
     * @return The found records as a List of Maps. Each Map is one record, with
     * column name as the key, and the column value as the value. The List will
     * be null if the query fails to return any records.
     * @throws SQLException if database access error or illegal sql
     * @throws Exception for all other problems
     */
    public List findRecords(String sqlString)
            throws SQLException, Exception {

        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData = null;
        final List list = new ArrayList();
        Map record = null;

        stmt = conn.createStatement();
        rs = stmt.executeQuery(sqlString);
        metaData = rs.getMetaData();
        final int fields = metaData.getColumnCount();

        while (rs.next()) {
            record = new LinkedHashMap();
            for (int i = 1; i <= fields; i++) {
                try {
                    record.put(metaData.getColumnName(i), rs.getObject(i));
                } catch (NullPointerException npe) {
                    // no need to do anything... if it fails, just ignore it and continue
                }
            } // end for
            list.add(record);
        } // end while

        return list; // will  be null if none found
    }

    /**
     * Retrieves a record based on the primary key of a table.
     *
     * @param table - a <code>String</code> representing the table name.
     * @param primaryKeyField - a <code>String</code> representing the primary
     * key field name used as the search criteria.
     * @param keyValue - an <code>Object</code> representing the primary key
     * field value used for the search criteria. Typically this is a String or
     * numeric typewrapper class.
     * @return a <code>Map</code> if the record is found; <code>null</code>
     * otherwise. The key is the columnName, the value is the field data.
     * @throws SQLException if database access error or illegal sql
     * @throws Exception for all other problems
     */
    public Map getRecordByID(String table, String primaryKeyField, Object keyValue)
            throws SQLException, Exception {

        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData = null;
        final Map record = new HashMap();

        stmt = conn.createStatement();
        String sql2;

        if (keyValue instanceof String) {
            sql2 = "= '" + keyValue + "'";
        } else {
            sql2 = "=" + keyValue;
        }

        final String sql = "SELECT * FROM " + table + " WHERE " + primaryKeyField + sql2;
        rs = stmt.executeQuery(sql);
        metaData = rs.getMetaData();
        metaData.getColumnCount();
        final int fields = metaData.getColumnCount();

        // Retrieve the raw data from the ResultSet and copy the values into a Map
        // with the keys being the column names of the table.
        if (rs.next()) {
            for (int i = 1; i <= fields; i++) {
                record.put(metaData.getColumnName(i), rs.getObject(i));
            }
        }

        return record;
    }

    /**
     * Inserts a record into a table based on a <code>List</code> of column
     * descriptors and a one-to-one mapping of an associated <code>List</code>
     * of column values.
     *
     * @param tableName - a <code>String</code> representing the table name
     * @param colDescriptors - <code>List</code> containing the column
     * descriptors
     * @param colValues - <code>List</code> containing the column values. The
     * order of these values must match the order of the column descriptors.
     * @return <code>true</code> if successfull; <code>false</code> otherwise
     * @throws SQLException if database access error or illegal sql
     * @throws Exception for all other problems
     */
    public boolean insertRecord(String tableName, List colDescriptors,
            List colValues) throws SQLException, Exception {

        PreparedStatement pstmt = null;
        int recsUpdated = 0;

        pstmt = buildInsertStatement(conn, tableName, colDescriptors);

        final Iterator i = colValues.iterator();
        int index = 1;
        while (i.hasNext()) {
            final Object obj = i.next();
            pstmt.setObject(index++, obj);
        }
        recsUpdated = pstmt.executeUpdate();

        if (recsUpdated == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Updates one or more records in a table based on a single, matching field
     * value.
     *
     * @param tableName - a <code>String</code> representing the table name
     * @param colDescriptors - a <code>List</code> containing the column
     * descriptors for the fields that can be updated.
     * @param colValues - a <code>List</code> containing the values for the
     * fields that can be updated.
     * @param whereField - a <code>String</code> representing the field name for
     * the search criteria.
     * @param whereValue - an <code>Object</code> containing the value for the
     * search criteria.
     * @return an <code>int</code> containing the number of records updated.
     * @throws SQLException if database access error or illegal sql
     * @throws Exception for all other problems
     */
    public int updateRecords(String tableName, List colDescriptors, List colValues,
            String whereField, Object whereValue) throws SQLException, Exception {

        PreparedStatement pstmt = null;
        int recsUpdated = 0;

        pstmt = buildUpdateStatement(conn, tableName, colDescriptors, whereField);

        final Iterator i = colValues.iterator();
        int index = 1;
        Object obj = null;

        // set params for column values
        while (i.hasNext()) {
            obj = i.next();
            pstmt.setObject(index++, obj);
        }
        // and finally set param for wehere value
        pstmt.setObject(index, whereValue);

        recsUpdated = pstmt.executeUpdate();

        return recsUpdated;
    }

    /**
     * Deletes one or more records in a table based on a single, matching field
     * value.
     *
     * @param tableName - a <code>String</code> representing the table name
     * @param whereField - a <code>String</code> representing the field name for
     * the search criteria.
     * @param whereValue - an <code>Object</code> containing the value for the
     * search criteria.
     * @return an <code>int</code> containing the number of records updated.
     * @throws SQLException if database access error or illegal sql
     * @throws Exception for all other problems
     */
    public int deleteRecords(String tableName, String whereField, Object whereValue)
            throws SQLException, Exception {

        PreparedStatement pstmt = null;
        int recsDeleted = 0;

        pstmt = buildDeleteStatement(conn, tableName, whereField);

        // delete all records if whereField is null
        if (whereField != null) {
            pstmt.setObject(1, whereValue);
        }

        recsDeleted = pstmt.executeUpdate();

        return recsDeleted;
    }

    /*
	 * Builds a java.sql.PreparedStatement for an sql insert
	 * @param conn - a valid connection
	 * @param tableName - a <code>String</code> representing the table name
	 * @param colDescriptors - a <code>List</code> containing the column descriptors for
	 * the fields that can be inserted.
	 * @return java.sql.PreparedStatement
	 * @throws SQLException
     */
    private PreparedStatement buildInsertStatement(Connection conn_loc, String tableName, List colDescriptors)
            throws SQLException {
        StringBuffer sql = new StringBuffer("INSERT INTO ");
        sql.append(tableName).append(" ");

        // now build column references
        StringJoiner sj = new StringJoiner(",", "(", ")");
        final Iterator i = colDescriptors.iterator();
        while (i.hasNext()) {
            sj.add((String) i.next());
        }
        sql.append(sj.toString());

        // now build VALUES references
        sql.append(" VALUES ");
        sj = new StringJoiner(",", "(", ")");
        for (Object col : colDescriptors) {
            sj.add("?");
        }
        sql.append(sj.toString());
        System.out.println(sql);
        return conn_loc.prepareStatement(sql.toString());
    }

    /*
	 * Builds a java.sql.PreparedStatement for an sql update using only one where clause test
	 * @param conn - a JDBC <code>Connection</code> object
	 * @param tableName - a <code>String</code> representing the table name
	 * @param colDescriptors - a <code>List</code> containing the column descriptors for
	 * the fields that can be updated.
	 * @param whereField - a <code>String</code> representing the field name for the
	 * search criteria.
	 * @return java.sql.PreparedStatement
	 * @throws SQLException
     */
    private PreparedStatement buildUpdateStatement(Connection conn_loc, String tableName,
            List colDescriptors, String whereField) throws SQLException {

        StringBuffer sql = new StringBuffer("UPDATE ");
        (sql.append(tableName)).append(" SET ");
        final Iterator i = colDescriptors.iterator();
        while (i.hasNext()) {
            (sql.append((String) i.next())).append(" = ?, ");
        }
        // now remove last comma
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")));

        // now do where clause
        ((sql.append(" WHERE ")).append(whereField)).append(" = ?");
        final String finalSQL = sql.toString();
        return conn_loc.prepareStatement(finalSQL);
    }

    /*
	 * Builds a java.sql.PreparedStatement for an sql delete using only one where clause test
	 * @param conn - a JDBC <code>Connection</code> object
	 * @param tableName - a <code>String</code> representing the table name
	 * @param whereField - a <code>String</code> representing the field name for the
	 * search criteria.
	 * @return java.sql.PreparedStatement
	 * @throws SQLException
     */
    private PreparedStatement buildDeleteStatement(Connection conn_loc, String tableName, String whereField)
            throws SQLException {
        final StringBuffer sql = new StringBuffer("DELETE FROM ");
        sql.append(tableName);

        // delete all records if whereField is null
        if (whereField != null) {
            sql.append(" WHERE ");
            (sql.append(whereField)).append(" = ?");
        }

        final String finalSQL = sql.toString();
//		System.out.println(finalSQL);
        return conn_loc.prepareStatement(finalSQL);
    }

    public static void main(String[] args) throws Exception {
        DbStrategy db = new MySqlDbStrategy();
        db.openConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book",
                "root", "admin");

        db.insertRecord("author", Arrays.asList("author_name", "date_added"), 
                Arrays.asList("Peter Mann", new java.util.Date()));
        
//            db.updateRecords("author", Arrays.asList("author_name","date_added"),
//                    Arrays.asList("Peter Manni",new java.util.Date()),
//                    "author_id",3);

    }

} // end class
