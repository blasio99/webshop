package dev.blasio99.webshop.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {


	@Autowired
 	BCryptPasswordEncoder bCryptPasswordEncoder;
	
    @Autowired
	private UserDetailsService userDetailsService;

	@Autowired
    @Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
		
		// No session will be created or used by spring security
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors()
			.and ()
			.csrf().disable()
            .authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/register/**").permitAll()
			//.antMatchers("/admin/**").permitAll()
			//.antMatchers("/api/**").permitAll()
            .antMatchers("/api/**").hasAnyRole(String.valueOf("CLIENT"), String.valueOf("ADMIN"))
            .antMatchers("/admin/api/**").hasRole(String.valueOf("ADMIN"))
            .anyRequest()
            .authenticated()
            .and()
			.httpBasic();
    }

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
