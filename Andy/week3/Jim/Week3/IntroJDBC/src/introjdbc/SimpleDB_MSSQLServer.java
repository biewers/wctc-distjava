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
 * Here's a simple example of how to connect to and use a database from Java.
 * Note that this example sacrifices good OOP design for simplicity. Do you
 * think you can identify ways to improve on this design?
 * 
 * Instructions: You need four or five things to work with Java Database Connectivity (JDBC):
 *    1. A suitable database driver (prefere Type 4). We'll use the Microsoft SQL Server driver
 *    2. The driver needs to be on the classpath, which means it must be in your libraries directory.
 *    3. Acess to a database. We'll use WCTC's sample Employee database, accessible from anywhere.
 *    4. A database URL (Uniform Resource Locator) string, which is essentially the address 
 *       of your database or database server.
 *    5. The fully qualified name of the driver class
 *    6. a user name and password providing access to the database.
 *
 * @author Jim Lombardo
 */
public class SimpleDB_MSSQLServer {
	private Connection conn;
	private String driverClassName;
	private String url;
	private String userName;
	private String password;

	public static void main(String[] args) {
		// First instantiate and store an object of this class
		SimpleDB_MSSQLServer db = new SimpleDB_MSSQLServer();

		/*
		 * ============= JDBC INITIALIZATION ==================
		 */

		// STEP 1: Set the fully qualified name of the JDBC driver class
		// (Here we use the Microsoft driver .jar provided on Blackboard
		// Did you remember to import java.sql.* ?
		db.driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

		// STEP 2: Set the driver-specific URL (Uniform Resource Locator)
		// This is a server-specific address format
		db.url = "jdbc:sqlserver://bitsql.wctc.edu:1433;databaseName=JGL-EMPLOYEE";

		// STEP 3: Set user name and password (if required)
		db.userName = "advjava";
		db.password = "advjava";

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
			  "Error: Failed to load JDBC driver!" );
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
//		String sql = "SELECT * FROM [dbo].[EMPLOYEE] WHERE "
//                        + "HIREDATE > '1/1/1988' ORDER BY LASTNAME";
                String sql = "SELECT LASTNAME, FIRSTNAME, EMAIL, HIREDATE FROM [dbo].[EMPLOYEE] "
                        + "ORDER BY EMAIL DESC";
                String sql2 = "DELETE FROM [dbo].[EMPLOYEE] WHERE LASTNAME='Berger' AND FIRSTNAME = 'Ham'";

		try {
			// Next use the connection object created earlier to create a statement object
			stmt = db.conn.createStatement();

			// Then use the executeQuery() method of the statement object
			// to execute the read-only query.
			// Be sure to check all the methods of the statement object in the API.
			// You would use an updateQuery() method, e.g., to insert or edit records
//                        int recordsDeleted = stmt.executeUpdate(sql2);
			rs = stmt.executeQuery(sql);

                        

                        System.out.println("============================");
                        System.out.println("Output from SQL Server...");
                        System.out.println("============================");
                        
//                        rs.next();
//                        String firstName = rs.getString("FIRSTNAME");

                        // The ResultSet contains the records returned by your query.
			// Loop through the ResultSet to extract the data
			int count = 0;
			while( rs.next() ) {
				// Each record contains three display fields which we will reference
				// by number (1 based). Read up on other methods we could use in ResultSet.
                                System.out.println("\nRecord No: " + (count + 1));
//                                System.out.println("\nID: " + rs.getInt(1)); // column one (ResultSet field)
				System.out.println( "Last Name: " + rs.getString(1) ); // named field
				System.out.println( "First Name: " + rs.getObject("FIRSTNAME") );
				System.out.println( "Email: " + rs.getObject("EMAIL") );
				System.out.println( "Hire Date: " + rs.getObject("HIREDATE") );
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
