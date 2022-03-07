
package com.abn.dish.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

 
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		
		 httpSecurity.csrf().disable()
		        .authorizeRequests()
		        .antMatchers("/actuator/**").hasAnyRole("admin")
		        .antMatchers("/api/**").hasAnyRole("user")
		        .and().formLogin();
		  }
	}


