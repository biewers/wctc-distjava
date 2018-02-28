package introjdbc;
/*
 * @(#)SimpleDB.java	2.00 May 19, 2004
 *
 * Copyright (c) 2003-2004 Waukesha County Technical College. All Rights Reserved.
 *
 * THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF
 * THE ACADEMIC FREE LICENSE V2.0 ("AGREEMENT"). ANY USE, REPRODUCTION
 * OR DISTRIBUTION OF THE PROGRAM CONSTITUTES RECIPIENT'S ACCEPTANCE
 * OF THIS AGREEMENT. A COPY OF THE AGREEMENT MUST BE ATTACHED TO ANY
 * AND ALL ORIGINALS OR DERIVITIVES.
 */

// Mandatory for JDBC
import java.sql.*;

/**
 * Here's a simple example of how to connect to and use a Oracle database from Java.
 * 
 * IMPORTANT: this database no longer exists at WCTC. The code is provided
 * as an example only, but you cannot execute this code without a database
 * that has the required tables/fields.
 * 
 * Instructions: You need four or five things to work with Java Database Connectivity (JDBC):
 *    1. A suitable database driver (prefere Type 4 -- we'll use the excellent 
 *       Oracle 9i/10g driver, which needs to be on the classpath.
 *    2. You will also need to know the fully qualified name of the driver class, which is 
 *       normally provided in the driver documentation.
 *    3. Acess to a database. We'll use a remote Oracle database server at WCTC.
 *    4. A database URL (Uniform Resource Locator) string, which is essentially the address 
 *       of your database or database server. Or, you could also use a JNDI address provided
 *       by a server administrator. JNDI (Java Naming and Directory Interface) is used to 
 *       provided an abstraction of a database connection that programmers can use without 
 *       the need to know the URL or possess the credentials. 
 *    5. User name and password credentials if not using a JNDI address.
 *
 * @author Jim Lombardo
 */
public class SimpleDB_Oracle {
	private Connection conn;
	private String driverClassName;
	private String url;
	private String userName;
	private String password;
	private String employeeEmail;

	public static void main(String[] args) {
		// First instantiate and reference an object of this class
		SimpleDB_Oracle db = new SimpleDB_Oracle();

		/*
		 * ============= JDBC INITIALIZATION ==================
		 */

		// STEP 1: Set the fully qualified name of the JDBC driver class
		// (Here we reference the Oracle Type 4 driver shipped with Oracle 10g)
		// Did you remember to import java.sql.* ?
		db.driverClassName = "oracle.jdbc.driver.OracleDriver";

		// STEP 2: Set the driver-specific URL (Uniform Resource Locator)
		// Here we show you an example of what an Oracle URL looks like.
                // However, please note: we no longer have this server and this
                // will not connect to anything.
		db.url = "jdbc:oracle:thin:@orabit.wctc.edu:1521:orcl";

		// STEP 3: Set user name and password (if not using JNDI -- we're not)
		db.userName = "ADVJAVA";
		db.password = "ADVJAVA";

		// STEP 4: Now make a connection to the database
		// Checked exceptions are thrown, so we must use handle or re-throw
		try {
			  // Calling the Class.forName automatically creates an instance of a driver 
			  // and registers it with the DriverManager, so you don't need to create an instance of the class
			  Class.forName (db.driverClassName);

			  // Now let the Driver Manager use the class to create a connection object.
			  // As part of its initialization, the DriverManager class attempts to load the 
			  // driver classes referenced in the jdbc.drivers system property, which is updated
			  // automatically when Class.forName(db.driverClassName) is called (see above).
			  db.conn = DriverManager.getConnection(db.url, db.userName, db.password);
		}
		catch ( ClassNotFoundException cnfex ) {
		   System.err.println(
			  "Error: Failed to load JDBC/ODBC driver!" );
		   cnfex.printStackTrace();
		   System.exit( 1 );  // terminate program
		}
		catch ( SQLException sqlex ) {
		   System.err.println( "Error: Unable to connect to database!" );
		   sqlex.printStackTrace();
		   System.exit( 1 );  // terminate program
		}

		/*
		 * =========== Now Use JDBC to Work With Your Database ================================
		 */

		// We'll need a statement object to execute the sql query (a statement)
		Statement stmt = null;

		// We'll need one of these to hold results returned from our query.
		// Remember, not all queries return results.
		ResultSet rs = null;

		// Now create an sql query string (most sql is standard, but
		// some database products use proprietary code like this for Oracle)
//		String sql = "SELECT LASTNAME, FIRSTNAME, EMAIL, HIREDATE FROM JLOBO.EMPLOYEE " +
//			"WHERE HIREDATE > TO_DATE('01-01-1988','MM-DD-RRRR') ORDER BY HIREDATE DESC";
		String sql = "SELECT LASTNAME, FIRSTNAME, EMAIL, HIREDATE FROM JLOBO.EMPLOYEE " +
			"WHERE HIREDATE > '01-JAN-1988' ORDER BY HIREDATE DESC";
		try {
			// Next use the connection object created earlier to create a statement object
			stmt = db.conn.createStatement();

			// Then use the executeQuery() method of the statement object
			// to execute the read-only query.
			// Be sure to check all the methods of the statement object in the API.
			// You would use an updateQuery() method, e.g., to insert or edit records
			rs = stmt.executeQuery(sql);

			// The ResultSet contains the records returned by your query.
			// Loop through the ResultSet to extract the data
			int count = 0;
                        
                        System.out.println("============================");
                        System.out.println("Output from Oracle Server...");
                        System.out.println("============================");
			
			while( rs.next() ) {
				// Each record contains three display fields which we will reference
				// by number (1 based). Read up on other methods we could use in ResultSet.
				// Note that we can use column numbers (one-based) or column names as
				// parameters for the rs.getXXXX methods
                                System.out.println("\nRecord No. " + (count + 1));
				System.out.println( "LastName: " + rs.getString("LASTNAME") ); // column one
				System.out.println( "FirstName: " + rs.getString(2) ); // column two
				db.employeeEmail = rs.getString(3);
				System.out.println( "Email: " + db.employeeEmail ); // column three
				System.out.println( "HireDate: " + rs.getDate(4) ); // column three
				count++;
			}
			System.out.println( "==================\n" + count + " records found." );
			
			/*
			 * ======= Now try UPDATING one of these records =====================
			 */
			//System.out.println("\nUpdating last record ...\n...changing LastName to Smith...");
			
			//sql = "UPDATE JLOBO.EMPLOYEE SET LASTNAME = 'Smith' WHERE EMAIL = '" +
			//		db.employeeEmail + "'";
			
			// Now update the record. Notice we use excecuteUpdate() here, which returns
			// an integer value indicating the number of records updated. This also works
			// for insert and delete.
			//int recordsProcessed = stmt.executeUpdate(sql);
			//System.out.println("\nSuccessfully updated " + recordsProcessed + " record.");
			
			// To delete the record, change the sql above to this:
			// sql = "DELETE FROM JLOBO.EMPLOYEE WHERE EMAIL = '" + db.employeeEmail + "'";
			// recordsProcessed = stmt.executeUpdate(sql);
			
			
			// Inserting records takes the following form:
			// INSERT INTO table_name (column1, column2,...)
			// VALUES (value1, value2,....)
            sql = "INSERT INTO JLOBO.EMPLOYEE (LASTNAME, FIRSTNAME, EMAIL, HIREDATE) "
                    + "VALUES('Bush','George','gbush@isp.com',to_date('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'))";
			int recordsProcessed = stmt.executeUpdate(sql);
			
		} catch (SQLException sqle) {
			System.out.println(sqle);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			// Make sure we close the statement and connection objects no matter what.
			// Since these also throw checked exceptions, we need a nested try-catch
			try {
				stmt.close();
				db.conn.close();
			} catch(Exception e) {
				System.out.println(e);
			}
		}

	}
}
