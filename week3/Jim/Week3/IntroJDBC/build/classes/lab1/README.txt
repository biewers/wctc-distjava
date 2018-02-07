Instructions
------------------
This package contains a complete SOA (Service Oriented Architecture) for 
accessing a database. Note that everything starts with the HRService class.
If you were to use this SOA service your program would talk exclusively to
the HRService class.

In SOA for databases, the data flow is:

Service -> DAO -> Database Access class

In this app, the classes used are:

HRService -> EmployeeDAO (or DepartmentDAO) -> DBAccessor

NOTE:

Service classes can, and should talk to multiple DAOs that are needed by
the service. Also, service classes can, but are rarely implemented as
Strategy objects.

DAOs should decouple the service class from the low-level Database access
classes and provide business terminology (not software terminology). For
example in an EmployeeDAO we would write a method named "getAllEmployees"
instead of "getAllRecords". Also a DAO will often provide the database
connection information. Also, DAOs should be written as Strategy objects.

Low-level database access classes (e.g., DB_Generic) do the low-level
JDBC code to talk to the database. These too should be written as strategy
objects because the syntax for different database products may vary.

