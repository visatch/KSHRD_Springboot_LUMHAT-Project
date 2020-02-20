package com.kshrd.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private AuthenticationSuccessHandler successHandler;
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	@Autowired
	@Qualifier("userDetailServiceImp")
	private UserDetailsService userDetailsService;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
					.antMatchers("/classroom/js/**").permitAll()
					.antMatchers("/classroom/dist/**").permitAll()
					.antMatchers("/classroom/images/**").permitAll()
					.antMatchers("/classroom/font/**").permitAll()
					.antMatchers("/classroom/excel/**").permitAll()
					.antMatchers("/classroom/css/**").permitAll()
					.antMatchers("/classroom/**").hasAnyRole("ADMIN","USER")
				    .antMatchers("css/login.css").permitAll()
				    .antMatchers("/admin/**").hasRole("ADMIN")
				    .antMatchers("/profile/**","/do-quiz/**").hasAnyRole("ADMIN","USER")
			.and()
			.formLogin()
				.successHandler(successHandler);
		  http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessHandler(logoutSuccessHandler).deleteCookies("userId");
		  http.exceptionHandling()
				  .authenticationEntryPoint(authenticationEntryPoint);
	}
}
