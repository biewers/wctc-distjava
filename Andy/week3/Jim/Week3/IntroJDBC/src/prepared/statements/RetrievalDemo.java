package prepared.statements;

import introjdbc.*;
import java.sql.*;

/**
 * This demo shows how to use a PreparedStatement to retrieve records
 * from a MySql database. (Backup sql files are in the sql.bu package).
 * 
 * Note that PreparedStatements are better than normal SQL because they are
 * faster, safer (security wise they can't be used for SQL Injection) and
 * in the author's opinion, easier to use because you don't have to do
 * String concatenation to create a complex query.
 * 
 * @author jlombardo
 */
public class RetrievalDemo {
	private Connection conn;
	private String driverClassName;
	private String url;
	private String userName;
	private String password;

	public static void main(String[] args) {

		RetrievalDemo db = new RetrievalDemo();
		db.driverClassName = "com.mysql.jdbc.Driver";
		db.url = "jdbc:mysql://localhost:3306/hr";
		db.userName = "root";
		db.password = "admin";

		try {
			  Class.forName (db.driverClassName);
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
                
//		Don't use this: Statement stmt = null;
//                Use this:
            PreparedStatement pstmt = null;                
		ResultSet rs = null;

                // The ? symbol is a placeholder (parameter) for data
                // You can have as many as you need
                String sql = "select * from employee where active = ? and lastName = ?";
		try {
                    pstmt = db.conn.prepareStatement(sql);
                    pstmt.setInt(1, 3); // first param is 1-based position of ? symbol
                    pstmt.setString(2, "Jones");
                    rs = pstmt.executeQuery();
                        
                        System.out.println("============================");
                        System.out.println("Output from MySql Server...");
                        System.out.println("============================");
                        
			int count = 0;
			while( rs.next() ) {
                            System.out.println("\nRecord No: " + (count + 1));
                            System.out.println( "ID: " + rs.getInt("employee_id") ); // named field
                            System.out.println( "Last Name: " + rs.getString("last_name") ); // named field
                            System.out.println( "First Name: " + rs.getString("first_name") );
                            System.out.println( "Phone: " + rs.getString("phone") );
                            System.out.println("Active Status: " 
                                    + ((rs.getInt("active") == 1) ? "Active" : "Inactive"));
                            System.out.println("Entry Date: " + rs.getObject("entry_date"));
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
				pstmt.close();
				db.conn.close();
			} catch(Exception e) {
				System.out.println(e);
			}
		}

	}
}
