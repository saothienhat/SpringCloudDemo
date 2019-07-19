package com.saothienhat.apigatewayauth.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * class to setup JwtTokenFilter
 */
public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilterConfigurer.class);
    private JwtTokenProvider jwtTokenProvider;
    private AuthFilter authFilter;

    public JwtTokenFilterConfigurer(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public JwtTokenFilterConfigurer(JwtTokenProvider jwtTokenProvider, AuthFilter authFilter) {
    	this.jwtTokenProvider = jwtTokenProvider;
    	this.authFilter = authFilter;
	}

	@Override
    public void configure(HttpSecurity http) throws Exception {
    	LOGGER.info("###==> configuring to add JwtTokenFilter before process API...: " + this.jwtTokenProvider);
        // JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
    	JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider, this.authFilter);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
