package edu.wctc.dj.week14.namesapp14.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// require all requests to be authenticated except for the resources
		http.authorizeRequests().antMatchers("/javax.faces.resource/**")
			.permitAll().anyRequest().authenticated();
		// login
		http.formLogin().loginPage("/login.xhtml").permitAll()
			.failureUrl("/login.xhtml?error=true");
		// logout
		http.logout().logoutSuccessUrl("/login.xhtml");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user").password("password").roles("USER");
	}

}
