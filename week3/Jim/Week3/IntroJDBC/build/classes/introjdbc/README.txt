Instructions
------------------
This package contains very simple, one-class examples of connecting to
various types of databases. This code DOES NOT follow good OOP architecture
principles and is intended only to simplify the presentation of the steps
necessary to connect to a database and perform C.R.U.D. commands. For a more
architecturally sound example, see the package "lab1".

Also, please note that the Derby sample requires that you start the "sample"
database under the "Service" tab.

The MySql demo connects to a MySql server on campus. This demo works, but only
within the walls of our labs.

We will use both the Derby and the MySql samples for our classroom discussion.

The MSAccess sample is designed to show how to code to a MSAccess database.
You will not be able to run this examle, however, because you would need the
sample database file "employees.mdb" and a ODBC DSN established on a
Windows host computer.

The MSSQLServer sample requires a connectin to a MSSQL Server 2008 installation
and requires the sample database "JGL-EMPLOYEE". This server is no longer
available on campus. The code is still viable however as an example.

The Oracle samples were written for an old Oracle database server that we
no longer have on campus. Still the sample code
is useful and instructive.

IMPORTANT: the database drivers for all of these have been provided as ".jar"
files at the root of the project folder. They have already been added to your
"Libraries" folder as well. In practice you would probably only need one
database driver.