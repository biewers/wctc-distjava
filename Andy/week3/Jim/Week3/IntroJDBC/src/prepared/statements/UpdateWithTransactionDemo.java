package prepared.statements;

import introjdbc.*;
import java.sql.*;

/**
 * This final demo using PreparedStatement does two database operations, the
 * combination of which is considered one large atomic transaction. By
 * atomic we mean that both sub-tasks must be completed successfully or the
 * whole thing is considered a failure. When that happens, the sub-tasks are
 * rolled back, meaning that the database is restored to its previous state.
 * 
 * @author jlombardo
 */
public class UpdateWithTransactionDemo {

    private Connection conn;
    private String driverClassName;
    private String url;
    private String userName;
    private String password;

    public static void main(String[] args) {

        UpdateWithTransactionDemo db = new UpdateWithTransactionDemo();
        db.driverClassName = "com.mysql.jdbc.Driver";
        db.url = "jdbc:mysql://localhost:3306/hr";
        db.userName = "root";
        db.password = "admin";

        try {
            Class.forName(db.driverClassName);
            db.conn = DriverManager.getConnection(db.url, db.userName, db.password);
        } catch (ClassNotFoundException cnfex) {
            System.err.println(
                    "Error: Failed to load JDBC driver!");
            cnfex.printStackTrace();
            System.exit(1);  // terminate program
        } catch (SQLException sqlex) {
            System.err.println("Error: Unable to connect to database!");
            sqlex.printStackTrace();
            System.exit(1);  // terminate program
        }

//		Don't use this: Statement stmt = null;
//                Use this:
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;

        // These two tasks will complete successfully. But if you
        // edit sql2 and replace the "_" in activity_log with a space,
        // the statement will fail because the sql is syntactically incorrect.
        // When this happens both tasks will be rolled back.
        String sql = "update employee set active = ? where last_name = ?";
        String sql2 = "insert into activity log (activity) values (?)";

        try {
            db.conn.setAutoCommit(false);
            pstmt = db.conn.prepareStatement(sql);
            pstmt2 = db.conn.prepareStatement(sql2);

            int activeStatus = 0;
            String lastName = "Smith";
            pstmt.setInt(1, activeStatus); // first param is 1-based position of ? symbol
            pstmt.setString(2, lastName);
            int recs = pstmt.executeUpdate();

            if(activeStatus == 0) {
                pstmt2.setString(1, "made employee with last name = " 
                        + lastName + " INACTIVE");
            } else {
                pstmt2.setString(1, "made employee with last name = " 
                        + lastName + " ACTIVE");
            }
            int recs2 = pstmt2.executeUpdate();

            db.conn.commit();
            
            System.out.println(
                    "Total records processed for two sub-tasks in transaction: " 
                    + (recs + recs2));

        } catch (SQLException sqle) {
            System.out.println(sqle);
            if (db.conn != null) {
                try {
                    System.err.print("Transaction is being rolled back\n");
                    db.conn.rollback();
                } catch (SQLException excep) {
                    System.out.println(excep);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
           try {
                    System.err.print("Transaction is being rolled back\n");
                    db.conn.rollback();
                } catch (SQLException excep) {
                    System.out.println(excep);
                }
        } finally {
            // Make sure we close the statement and connection objects no matter what.
            // Since these also throw checked exceptions, we need a nested try-catch
            try {
                pstmt.close();
                db.conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
}
