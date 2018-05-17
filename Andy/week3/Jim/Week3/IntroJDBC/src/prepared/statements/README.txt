Instructions:
----------------
These are simple examples of using JDBC Prepared Statements to improve 
flexibility and performance, and to help eliminate the security threat of
a SQL Injection attack.

Flexibility is gained by parameterized queries.

Performance is improved because the Prepared Statements are pre-compiled by
the database server (as opposed to compile-on-each-query, which is what 
happens if you do not used Prepared Statements).

Security is enhanced because hackers cannot modify the sql statement with
malicious code. The parameters protect against this. You cannot substitute
a parameter, e.g., with a DELETE statement.

