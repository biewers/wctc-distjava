package prepared.statements;

import introjdbc.*;
import java.sql.*;

/**
 * Another PreparedStatement demo, but this one is used to update a record.
 * 
 * @author jlombardo
 */
public class UpdateDemo {
	private Connection conn;
	private String driverClassName;
	private String url;
	private String userName;
	private String password;

	public static void main(String[] args) {

		UpdateDemo db = new UpdateDemo();
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
//		ResultSet rs = null;

                // The ? symbol is a placeholder (parameter) for data
                // You can have as many as you need
                String sql = "update employee set active = ? where last_name = ?";

		try {
			pstmt = db.conn.prepareStatement(sql);
                        pstmt.setInt(1, 0); // first param is 1-based position of ? symbol
                        pstmt.setString(2, "Smith");
                        
                        System.out.println("============================");
                        System.out.println("Output from MySql Server...");
                        System.out.println("============================");
                        
			int recsAffected = pstmt.executeUpdate();
			System.out.println("Records affected: " + recsAffected);
                        
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
