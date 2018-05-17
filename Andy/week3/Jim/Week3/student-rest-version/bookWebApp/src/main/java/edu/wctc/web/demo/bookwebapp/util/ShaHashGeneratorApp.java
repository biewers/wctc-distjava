package edu.wctc.web.demo.bookwebapp.util;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * This app will generate a salted, SHA-512 hashed password based on the 
 * username and password field values in the users table in the db. Use this 
 * to initially seed your database with the hashed passwords. NOTE that you
 * can just run this class as a standalone app. A similar routine is provided
 * in the web app for registering new users. You only need to seed the
 * database with one account -- an admin account. From there on you can use
 * the web app instead of this program to create new users.
 * 
 * @author Jim Lombardo, james.g.lombardo@gmail.com
 */
public class ShaHashGeneratorApp {

    /**
     * @param args the command line arguments - not used.
     */
    public static void main(String[] args) {
        // modify these as necessary...
        String salt = "testuser@isp.com"; // username field in db
        String password = "testuser"; // password field in db
        System.out.println(password + ": " + sha512(password,salt));

        salt = "testmgr@isp.com"; // username field in db
        password = "testmgr"; // password field in db
        System.out.println(password + ": " + sha512(password,salt));

    }

    public static String sha512(String pwd, String salt) {

            ShaPasswordEncoder pe = new ShaPasswordEncoder(512);
            pe.setIterations(1024);
            String hash = pe.encodePassword(pwd, salt);

            return hash;
     
    }
}
