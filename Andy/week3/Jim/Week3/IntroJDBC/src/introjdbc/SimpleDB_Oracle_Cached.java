package introjdbc;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import oracle.jdbc.rowset.OracleCachedRowSet;
// Sun Refernce implementation not used in this example

/**
 * Here's a simple example of how to use a disconnected, CachedRowSet
 * implementation (by Oracle). One could also use the Sun Reference
 * implementation (
 * 
 * IMPORTANT: this database no longer exists at WCTC. The code is provided
 * as an example only, but you cannot execute this code without a database
 * that has the required tables/fields.
 * 
 * @author Jim Lombardo
 */
public class SimpleDB_Oracle_Cached {
	private Connection conn;
	private String driverClassName;
	private String url;
	private String userName;
	private String password;
    private String employeeEmail;

	public static void main(String[] args) {
		SimpleDB_Oracle_Cached db = new SimpleDB_Oracle_Cached();

		db.driverClassName = "oracle.jdbc.driver.OracleDriver";

                // Please note: we no longer have this server and this
                // will not connect to anything.
		db.url = "jdbc:oracle:thin:@orabit.wctc.edu:1521:orcl";
		db.userName = "ADVJAVA";
		db.password = "ADVJAVA";

		// Checked exceptions demand try-catch
		try {
            // This loads the class into memory
			Class.forName (db.driverClassName);

            CachedRowSet rs = new OracleCachedRowSet();
            rs.setUsername(db.userName);
            rs.setPassword(db.password);
            rs.setUrl(db.url);

            String sql = "SELECT LASTNAME, FIRSTNAME, EMAIL, HIREDATE " +
                    "FROM JLOBO.EMPLOYEE WHERE " +
                    "HIREDATE > TO_DATE('01-01-1988','MM-DD-RRRR') " +
                    "ORDER BY HIREDATE DESC";

            rs.setCommand(sql);
            rs.execute();

            System.out.println("============================");
            System.out.println("Output from Oracle CachedRowSet..");
            System.out.println("============================");

			int count = 0;
			while( rs.next() ) {
                System.out.println("\nRecord No. " + (count + 1));
				System.out.println( "LastName: " + rs.getString("LASTNAME") );
				System.out.println( "FirstName: " + rs.getString(2) );
				db.employeeEmail = rs.getString(3);
				System.out.println( "Email: " + db.employeeEmail );
				System.out.println( "HireDate: " + rs.getDate(4) );
				count++;
			}
			System.out.println( "==================\n" + count +
                    " records found." );

            // now update one row
            rs.setReadOnly(false);
            rs.absolute(2);
            rs.updateString("LASTNAME", "Karter");
            rs.updateRow();
            rs.setReadOnly(true);
            rs.acceptChanges();
			System.out.println( "==================\nRecord #2 updated successfully!" );
		}
		catch ( ClassNotFoundException cnfex ) {
		   System.err.println(
			  "Error: Failed to load JDBC/ODBC driver!" );
		   cnfex.printStackTrace();
		   System.exit( 1 );  // terminate program
		} catch ( SQLException sqlex ) {
		   System.err.println( "Error: Unable to connect to database!" );
		   sqlex.printStackTrace();
		   System.exit( 1 );  // terminate program
		} catch ( Exception ex ) {
		   System.err.println( "Error: Unable to connect to database!" );
		   ex.printStackTrace();
		   System.exit( 1 );  // terminate program
		}

	} // end main
} // end class
