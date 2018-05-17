package edu.wctc.dj.week10.namesapp10.data;

import java.util.Properties;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.ReflectiveLoadTimeWeaver;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "edu.wctc.dj.week10.namesapp10")
public class PersistenceJNDIConfig {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
		throws NamingException {
		LocalContainerEntityManagerFactoryBean emf
			= new LocalContainerEntityManagerFactoryBean();

		emf.setDataSource(dataSource());
		emf.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter());
		// Following required to avoid looking for persistence.xml
		emf.setPackagesToScan("edu.wctc.dj.week10.namesapp10");
		emf.setPersistenceUnitName("names-pu");
		emf.setLoadTimeWeaver(new ReflectiveLoadTimeWeaver());

		Properties properties = new Properties();
		properties.setProperty("eclipselink.logging.level", "FINE");
		properties.setProperty("eclipselink.logging.level.sql", "FINE");
		properties.setProperty("eclipselink.logging.parameters", "true");
		emf.setJpaProperties(properties);
		emf.afterPropertiesSet();

		return emf;
	}

	@Bean
	public DataSource dataSource() throws NamingException {
		return (DataSource) new JndiTemplate()
			.lookup("java:comp/env/jdbc/myNames");
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

}
