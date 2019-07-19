package com.saothienhat.apigatewayauth.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.saothienhat.apigatewayauth.model.APIGatewayConst;
import com.saothienhat.apigatewayauth.model.JwtProperties;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity 
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthFilter authFilter;
    
//    @Autowired
//	private JwtProperties jwtProperties;
    
    @Bean
	public JwtProperties jwtConfig() {
		return new JwtProperties();
	}

    /**
     * define which resources are public and which are secured
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	LOGGER.info("#####==> configuration to IGNORE /**/signin/** ...");
    	
    	// Disable CSRF (cross site request forgery)
        http.cors().and().csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.authorizeRequests()
                .antMatchers("/**/signin/**").permitAll()
                // Disallow everything else..
                .anyRequest().authenticated();

        // If a user try to access a resource without having enough permissions
        http.exceptionHandling().accessDeniedPage("/login");

        // Apply JWT
        // http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider, authFilter));

        // Optional, if you want to test the API from a browser
        // http.httpBasic();

//        // Disable CSRF (cross site request forgery)
//        http.cors().and().csrf().disable();
//
//        // No session will be created or used by spring security
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        // Entry points
//        http.authorizeRequests()
//                .antMatchers("/**/signin/**").permitAll()
//                .antMatchers("/register").permitAll()      
//                .antMatchers("/login").permitAll()
//                .antMatchers("/signin").permitAll()
//                .antMatchers(HttpMethod.POST, "/register").permitAll()
////                .antMatchers(HttpMethod.GET, "/error").permitAll() // FIXME: why this API happened ==> check it ?
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                // .antMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")
//                // Disallow everything else..
//                .anyRequest().authenticated();
//
//        // If a user try to access a resource without having enough permissions
//        http.exceptionHandling().accessDeniedPage("/login");
//
//        // Apply JWT
//        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
//
//        // Optional, if you want to test the API from a browser
//        // http.httpBasic();
//        
//        // Or:
////        http
////        .httpBasic().disable()
////        .csrf().disable()
////        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////        .and()
////            .authorizeRequests()
////            .antMatchers("/auth/signin").permitAll()
////            .antMatchers(HttpMethod.GET, "/vehicles/**").permitAll()
////            .antMatchers(HttpMethod.DELETE, "/vehicles/**").hasRole("ADMIN")
////            .antMatchers(HttpMethod.GET, "/v1/vehicles/**").permitAll()
////            .anyRequest().authenticated()
////        .and()
////        .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    	LOGGER.info("#####==> configuration to IGNORE http://localhost:8080/eureka/ ...");
        // Allow Eureka client to be accessed without authentication
        web.ignoring().antMatchers("/*/")//
                .antMatchers("/eureka/**")//
                .antMatchers(HttpMethod.OPTIONS, "/**"); // Request type options should be allowed.
    }

    /**
     * Inject PasswordEncoder for comparing password
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
    	LOGGER.info("#####==> init PasswordEncoder ...");
        return new BCryptPasswordEncoder(APIGatewayConst.CRYPT_PASSWORD_ENCODER_STRENGTH);
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
    	LOGGER.info("#####==> customAuthenticationManager() ...");
        return authenticationManager();
    }
}
