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
 * To be able to get at MS-Access, which was not designed for JDBC, and for which JDBC drivers 
 * are expensive and hard to find, we need to use ODBC so that the Sun driver can talk to 
 * the database. ODBC stands for "Open Database Connectivity" and is an industry standard,
 * non-platform specific way of talking to databases. The Sun driver is called a "bridge"
 * driver because it allows JDBC to talk to ODBC. Your instructor will show you how to setup
 * an ODBC connection to "Employees.mdb" but you MUST do this first!!!
 *
 * @author Jim Lombardo
 */
public class SimpleDB_MSAccess {
	private Connection conn;
	private String driverClassName;
	private String url;
	private String userName;
	private String password;

	public static void main(String[] args) {
		// First instantiate and store an object of this class
		SimpleDB_MSAccess db = new SimpleDB_MSAccess();

		/*
		 * ============= JDBC INITIALIZATION ==================
		 */

		// STEP 1: Set the fully qualified name of the JDBC driver class
		// (Here we use the JDBC-ODBC bridge driver shipped with Sun JDK)
		// Did you remember to import java.sql.* ?
		db.driverClassName = "sun.jdbc.odbc.JdbcOdbcDriver";

		// STEP 2: Set the driver-specific URL (Uniform Resource Locator)
		// Here we use a URL created by pointing the
		// JDBC-ODBC Bridge at the MS-Access ODBC System DSN ("EmployeeDSN")
		db.url = "jdbc:odbc:EmployeesDSN";

		// STEP 3: Set user name and password (if required)
		// MS-Access doesn't require (unless you do) these
		db.userName = "";
		db.password = "";

		// STEP 4: Now make a connection to the database
		// Checked exceptions demand try-catch
		try {
			  // This loads the class into memory
			  Class.forName (db.driverClassName);

			  // Now let the Driver Manager use the class to create a connection object
			  // It figures this out from the url
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
		// some database products use proprietary code, like this one for MS-Access)
		String sql = "SELECT LastName, FirstName, Email, HireDate FROM tblEmployee " +
						   "WHERE HireDate > #1/1/1988#"; // % is a wildcard: starts with L
		try {
			// Next use the connection object created earlier to create a statement object
			stmt = db.conn.createStatement();

			// Then use the executeQuery() method of the statement object
			// to execute the read-only query.
			// Be sure to check all the methods of the statement object in the API.
			// You would use an updateQuery() method, e.g., to insert or edit records
			rs = stmt.executeQuery(sql);

                        System.out.println("============================");
                        System.out.println("Output from MS-Access...");
                        System.out.println("============================");

                        // The ResultSet contains the records returned by your query.
			// Loop through the ResultSet to extract the data
			int count = 0;
			while( rs.next() ) {
				// Each record contains three display fields which we will reference
				// by number (1 based). Read up on other methods we could use in ResultSet.
                                System.out.println("\nRecord No. " + (count + 1));
				System.out.println( "LastName: " + rs.getString(1) ); // column one (ResultSet field)
				System.out.println( "FirstName: " + rs.getString(2) ); // column two
				System.out.println( "Email: " + rs.getString(3) ); // column three
				System.out.println( "HireDate: " + rs.getDate(4) ); // column three
				count++;
			}
			System.out.println( "==================\n" + count + " records found." );
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
