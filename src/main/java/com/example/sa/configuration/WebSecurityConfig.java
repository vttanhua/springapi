package com.example.sa.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

@Override
protected void configure(HttpSecurity http) throws Exception {	
	http
		.csrf()
		.and()
		.oauth2Login()
		.and()
		.authorizeRequests()
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
			.permitAll()
		.mvcMatchers("/", "/health", "/register","/api/v1/","/health", "/register").permitAll()
		.anyRequest().authenticated();
	}
/*
    @Override
    public void configure(WebSecurity web) throws Exception {
        // Allow access to everywhere
        web.ignoring().antMatchers("/**");//
        
        // No session will be created or used by spring security
        getHttp().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

      //  getHttp().addFilterBefore(new com.example.sa.security.JwtTokenFilter(clientDetailsService), UsernamePasswordAuthenticationFilter.class);

}*/
    
}
