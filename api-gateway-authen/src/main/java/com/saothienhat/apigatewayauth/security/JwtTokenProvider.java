package com.saothienhat.apigatewayauth.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.saothienhat.apigatewayauth.model.APIGatewayConst;
import com.saothienhat.apigatewayauth.model.JwtProperties;
import com.saothienhat.apigatewayauth.model.MongoUserDetails;
import com.saothienhat.apigatewayauth.model.PostgresUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
	private static final String AUTH = "auth";
	private static final String AUTHORIZATION = "Authorization";
	private String secretKey;

//	@Autowired
//	JwtProperties jwtProperties;

//	@Autowired
//	private UserDetailsService userDetailsService;

	@PostConstruct
	protected void init() {
		secretKey = APIGatewayConst.JWT.SECRET_KEY;
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		LOGGER.info("###==> secretKey: " + secretKey);
	}

	public String createToken(String username, List<String> roles) {

		Claims claims = Jwts.claims().setSubject(username);
		claims.put(AUTH, roles);

		Date now = new Date();
		Date validity = new Date(now.getTime() + APIGatewayConst.JWT.VALIDITY_IN_MILLISECONDS);

		String token = Jwts.builder()//
				.setClaims(claims)//
				.setIssuedAt(now)//
				.setExpiration(validity)//
				.signWith(SignatureAlgorithm.HS256, secretKey)//
				.compact();
		// jwtTokenRepository.save(new JwtToken(token));
		LOGGER.info("###==> created token: " + token);
		return token;
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader(AUTHORIZATION);
		/*
		 * if (bearerToken != null && bearerToken.startsWith("Bearer ")) { return
		 * bearerToken.substring(7, bearerToken.length()); }
		 */
		if (bearerToken != null) {
			return bearerToken;
		}
		return null;
	}

//	public boolean validateToken(String token) throws JwtException, IllegalArgumentException {
//		Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token); //
//		if (claims.getBody().getExpiration().before(new Date())) {
//			return false;
//		}
//		return true;
//	}
	
	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			boolean isValidToken = (claims.getBody().getExpiration().before(new Date())) ? false : true;
			return isValidToken;
		} catch (JwtException | IllegalArgumentException e) {
			throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
		}
	}

	public boolean isTokenPresentInDB(String token) {
		return true; // FIXME
//        return jwtTokenRepository.findById(token).isPresent();
	}

	// user details with out database hit
	public UserDetails getUserDetailsByToken(String token) {
		String userName = getUsername(token);
		List<String> roleList = getRoleListByToken(token);
		UserDetails userDetails = new PostgresUserDetails(userName, roleList.toArray(new String[roleList.size()]));
		LOGGER.info("  ---> UserDetail from Token: " + userDetails.toString());
		return userDetails;
	}

	public List<String> getRoleListByToken(String token) {
		return (List<String>) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(AUTH);
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public Authentication getAuthentication(String token) {
		LOGGER.info("###==> get Authentication with token: " + token);
		// using data base: uncomment when you want to fetch data from data base
		// UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
		// from token take user value. comment below line for changing it taking from data base
		UserDetails userDetails = getUserDetailsByToken(token);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

}
