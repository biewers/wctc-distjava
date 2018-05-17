Agenda
- Spring Boot
	- JSF
	- JPA
- Activity
- Project

Spring Boot
- Utilizes embedded Tomcat
- Simplest terms
	- Write 1 line of code for a complete application
- Our case: we will also configure JSF and JPA
- Spring boot sets us up to build REST services (next lesson)

Simplest Spring Boot Application

	@SpringBootApplication
	public class NamesApp {
	    public static void main(String[] args) {
	        SpringApplication.run(NamesApp.class, args);
	    }
	}

Activity Agenda
- Create project
- Setup project
- Create spring boot application
- Copy pages
- Copy sources
- Configure welcome page
- Reconfigure persistence configuration
- Additional required configuration

Create Project
- Simple Maven Java project (not web!)

Setup Project
- Maven parent
	- org.springframework.boot:spring-boot-starter-parent:2.0.1.RELEASE
- Dependencies
	- org.springframework.boot:spring-boot-starter-web
	- org.joinfaces:jsf-spring-boot-starter:3.1.1
	- org.springframework.boot:spring-boot-starter-data-jpa
	- org.primefaces:primefaces:5.0
	- org.apache.commons:commons-dbcp2:2.2.0
	- org.apache.derby:derbyclient:10.14.1.0
- NOTE: Notice how the spring boot dependencies have no version element

Create Spring Boot Application
- edu.wctc.dj.week12.namesapp12.NamesApp.java
- Run!

Copy XHTML Pages
- All static content goes under a new location
	- src/main/resources/META-INF/resources

Copy Sources
- Packages
	- beans
	- data - rename PersistenceJNDIConfig to PersistenceJDBCConfig
	- data.dao
	- model
	- service - NameService#getName uses INameDao#getOne() now

Configure Welcome Page
- edu.wctc.dj.week12.namesapp12.WelcomePage.java

Reconfigure Persistence Config
- Hibernate - just to be different
- Connection pool - something Glassfish used to do for us
- derbyclient library

Additional Required Configuration
- src/main/resources/application.properties
	- server.servlet.context-path=/NamesApp12
	- server.tomcat.additional-tld-skip-patterns=derbyLocale_*.jar

Project 9 - Spring Boot
- Create a spring boot version of your application
