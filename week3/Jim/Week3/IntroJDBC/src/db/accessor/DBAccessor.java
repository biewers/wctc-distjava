package db.accessor;

/*
 * @(#)DBAccessor 1.52 5/3/2006
 * Author: Jim Lombardo, WCTC Lead Java Instructor
 *
 * Copyright (c) 2003-2005 Waukesha County Technical College.
 * All Rights Reserved.
 *
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF
 * THE ACADEMIC FREE LICENSE V2.0 ("AGREEMENT"). ANY USE, REPRODUCTION
 * OR DISTRIBUTION OF THE PROGRAM CONSTITUTES RECIPIENT'S ACCEPTANCE
 * OF THIS AGREEMENT. A COPY OF THE AGREEMENT MUST BE ATTACHED TO ANY
 * AND ALL ORIGINALS OR DERIVITIVES.
 */

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * An interface contract for database access classes. This contract
 * does not support stored procedures.
 * 
 * @author Jim Lombardo
 * @version 1.51 5/3/2005
 *
 */
public interface DBAccessor {
	
	/**
	 * A utility method to expicitly open a db connection. Note that the
	 * connection will remain open until explicitly closed by member methods.
	 * 
	 * @param driverClassName - the fully qualified name of the driver class.
	 * @param url - the connection URL, driver dependent.
	 * @param username for database access permission, if required. Null and "" values
	 * are allowed.
	 * @param password for database access persmission, if required. Null and "" values
	 * are allowed
	 * @throws IllegalArgumentException if url is null or zero length
	 * @throws ClassNotFoundException if driver class cannot be found
	 * @throws SQLException if database access error occurs. For example, an invalid
	 * url could cause this; or, a database that is no longer available due to network
	 * or access permission problems.
	 */
	public abstract void openConnection(String driverClassName, String url, String username, String password) 
	throws IllegalArgumentException, ClassNotFoundException, SQLException;

	
	/**
	 * A utility method to expicitly close a db connection. Pooled connections
	 * should never be closed, but rather returned to the pool.
	 * <p>
	 * As an alternative to using this method, other member methods is this
	 * class offer a boolean switch to close the connection automatically.
	 * 
	 * @throws SQLException if connection cannot be closed due to a db access error.
	 */
	public abstract void closeConnection() throws SQLException;

        public abstract List<Map<String, Object>> findAllRecords(String tableName, int maxRecords)
            throws SQLException;

        /**
	 * Can be used to perform general purpose sql queries. This is especially 
	 * useful when other methods in the public interface don't provide the 
	 * needed features (such as complex, multiple criteria statements).
	 * 
	 * @param sqlString - the sql statement (check your database for compatibility)
	 * @param closeConnection - true if connection should be closed automatically; if
	 * false, connection must be explicitly closed using the closeConnection method.
	 * @return The found records as a List of Maps. Each Map is one record, with
	 * column name as the key, and the column value as the value. The List will be
	 * null if the query fails to return any records.
	 * @throws SQLException if database access error or illegal sql
	 * @throws Exception for all other problems
	 */
	public abstract List findRecords(String sqlString, boolean closeConnection) throws SQLException,
			Exception;

	/**
	 * Retrieves a record based on the primary key of a table.
	 * 
	 * @param table - a <code>String</code> representing the table name.
	 * @param primaryKeyField - a <code>String</code> representing the primary key field
	 * name used as the search criteria.
	 * @param keyValue - an <code>Object</code> representing the primary key field value
	 * used for the search criteria. Typically this is a String or numeric typewrapper class.
	 * @param closeConnection - true if connection should be closed automatically; if
	 * false, connection must be explicitly closed using the closeConnection method.
	 * @return a <code>Map</code> if the record is found; <code>null</code> otherwise. The key
	 * is the columnName, the value is the field data.
	 * @throws SQLException if database access error or illegal sql
	 * @throws Exception for all other problems
	 */
	public abstract Map getRecordByID(String table, String primaryKeyField, Object keyValue, boolean closeConnection)
	throws SQLException, Exception;

	/**
	 * Inserts a record into a table based on a <code>List</code> of column descriptors
	 * and a one-to-one mapping of an associated <code>List</code> of column values.
	 * 
	 * @param tableName - a <code>String</code> representing the table name
	 * @param colDescriptors - <code>List</code> containing the column descriptors
	 * @param colValues - <code>List</code> containing the column values. The order of
	 * these values must match the order of the column descriptors.
	 * @param closeConnection - true if connection should be closed automatically; if
	 * false, connection must be explicitly closed using the closeConnection method.
	 * @return <code>true</code> if successfull; <code>false</code> otherwise
	 * @throws SQLException if database access error or illegal sql
	 * @throws Exception for all other problems
	 */
	public abstract boolean insertRecord(String tableName, List colDescriptors, List colValues, boolean closeConnection)
	throws SQLException, Exception;

	/**
	 * Updates one or more records in a table based on a single, matching field value.
	 * 
	 * @param tableName - a <code>String</code> representing the table name
	 * @param colDescriptors - a <code>List</code> containing the column descriptors for
	 * the fields that can be updated.
	 * @param colValues - a <code>List</code> containing the values for the fields that
	 * can be updated.
	 * @param whereField - a <code>String</code> representing the field name for the
	 * search criteria.
	 * @param whereValue - an <code>Object</code> containing the value for the search criteria.
	 * @param closeConnection - true if connection should be closed automatically; if
	 * false, connection must be explicitly closed using the closeConnection method.
	 * @return an <code>int</code> containing the number of records updated.
	 * @throws SQLException if database access error or illegal sql
	 * @throws Exception for all other problems
	 */
	public int updateRecords(String tableName, List colDescriptors, List colValues,
			 String whereField, Object whereValue, boolean closeConnection)
			 throws SQLException, Exception;

	/**
	 * Deletes one or more records in a table based on a single, matching field value.
	 * 
	 * @param tableName - a <code>String</code> representing the table name
	 * @param whereField - a <code>String</code> representing the field name for the
	 * search criteria.
	 * @param whereValue - an <code>Object</code> containing the value for the search criteria.
	 * @param closeConnection - true if connection should be closed automatically; if
	 * false, connection must be explicitly closed using the closeConnection method.
	 * @return an <code>int</code> containing the number of records updated.
	 * @throws SQLException if database access error or illegal sql
	 * @throws Exception for all other problems
	 */
	public abstract int deleteRecords(String tableName, String whereField, Object whereValue, boolean closeConnection)
	throws SQLException, Exception;
}