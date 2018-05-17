## Databases

# JDBC

- Java Database Connectivity

- Consistent interfaces
    - java.sql.Connection
    - java.sql.Statement
    - java.sql.ResultSet

- Vendor-specific drivers
    - Oracle, MSSQL, MySQL, etc

# Connection pools

- Enterprise Apps, such as Web Apps, utilize connection pools
- Motivation: Connecting to databases is an "expensive" operation
- Maintain a pool of connected database connections
- When a connection is needed, grab one from the pool
- When done with a connection, return it to the pool

# DataSource

- Java interface for obtaining a java.sql.Connection
- Connection#close() returns a connection to the pool

# How to Execute a Query using JDBC

- Obtain a connection
- Prepare a statement via the connection
    - SELECT statement
    - Bind parameters, if required
- Execute the statement and get a result set
- Iterate over the result set to get the data
- Close the statement and connection

## Activity

# Agenda

- Create week9 branch
- Copy NamesApp8 to NamesApp9
    - Update project properties name and group id
    - Rename packages to week9
- Setup a connection pool
- Add the pool to the Glassfish server (i.e., not just the project)
- Create a reference to the database in the web application
- Create the classes to communicate with the datbase
- Update the service to use the new classes

# Create names database
- name table
- address table

# Create JDBC Resource and Connection Pool
- Click New file
- Glassfish -> JDBC Resource

# Add glassfish-resources.xml to Glassfish resources
- Open Glassfish admin console
- Add glassfish-resources.xml to resources
    - Set user=APP
    - Set password=APP
    - Ping (test)
- Remove glassfish-resources.xml from project

# Add JDBC resource to web.xml
- Open web.xml
- Under References tab, add JDBC reference
- Click on Source tab and change javax.sql.DataSource to javax.sql.ConnectionPoolDataSource

# Create ConnectionUtil class
- public static Connection getConnection()
- public static void returnConnection(Connection)
- Test JNDI lookup

# Create NameDAO class
- public List<Name> getNames()
- public List<Name> getNames(String search)

# Create AddressDAO class
- public Address getAddress(Name name)

# Update NameService to use DAOs

