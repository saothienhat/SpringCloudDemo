package com.saothienhat.apigatewayauth.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.saothienhat.apigatewayauth.exception.CustomException;

import io.jsonwebtoken.JwtException;

public class JwtTokenFilter extends GenericFilterBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);
	private JwtTokenProvider jwtTokenProvider;
	private AuthFilter authFilter;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, AuthFilter authFilter) {
    	this.jwtTokenProvider = jwtTokenProvider;
    	this.authFilter = authFilter;
	}

	/**
     * This will filter all of API (except /login & /eureka api) to check token for authentication
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (request != null) {
//			String paramName = "accesstoken";
//	        String token = request.getParameter(paramName);
	        
//			String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
			String token = jwtTokenProvider.resolveToken(request);
			
			LOGGER.info("###==> doFilter => token: " + token);
			StringBuffer requestInfo = new StringBuffer();
			requestInfo.append("METHOD: " + request.getMethod()).append(" - ");
			String requestURI = request.getRequestURI();
			requestInfo.append("URI: " + requestURI).append(" - ");
			requestInfo.append("URL: " + request.getRequestURL());
			LOGGER.info("  ###==>> request info: " + requestInfo.toString());
			
			
			if (token != null) {
				if (!jwtTokenProvider.isTokenPresentInDB(token)) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
					throw new CustomException("Invalid JWT token", HttpStatus.UNAUTHORIZED);
				}
				try {
					jwtTokenProvider.validateToken(token);
				} catch (JwtException | IllegalArgumentException e) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
					LOGGER.error("###==> doFilter... ==> Invalid JWT token: " + e.getMessage());
					throw new CustomException("Invalid JWT token", HttpStatus.UNAUTHORIZED);
				}
				Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
				if (auth != null) {
					// setting auth in the context.
					int status = ((HttpServletResponse) response).getStatus();
					LOGGER.info("\t ++ User " + auth.getName() + " - Status: " + status);
					
					// Check Role
					boolean isAccessable = isAccessableAPI(requestURI, auth.getAuthorities()); 
					if(!isAccessable) {
						LOGGER.error("###==> doFilter... ==> user '" + auth.getName() + " CANNOT access to API: " + requestURI);
						throw new CustomException("Not Access to API", HttpStatus.UNAUTHORIZED);
					}
					
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
			filterChain.doFilter(req, res);
		} else {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Request**.");
		}
		
		
		
		LOGGER.info("###==> doFilter DONE !!!\n");
	}
    
	/**
	 * Check if current User has Role to access to API
	 * @param requestURI
	 * @param authorities
	 * @return true if API is accessable. Otherwire, return false
	 */
	private boolean isAccessableAPI(String requestURI, Collection<? extends GrantedAuthority> authorities) {
		boolean isAccessableAPI = false;
		for (GrantedAuthority grantedAuthority : authorities) {
			String authority = grantedAuthority.getAuthority();
			if(authFilter.validateRole(authority, requestURI)) {
				isAccessableAPI = true; break;
			}
		}
		return isAccessableAPI;
	}

	private boolean isAllowRequestWithoutToken(HttpServletRequest request) {
		if (request.getRequestURI().contains("/register")) {
			return true;
		}
		return false;
	}
}
