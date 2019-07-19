package com.saothienhat.apigatewayauth.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.saothienhat.apigatewayauth.model.RoleType;

@Component
public class AuthFilter {
	private final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);
	public static final Map<RoleType, List<String>> MAPPING = new HashMap<>();

	@PostConstruct
	protected void init() {
		LOGGER.info("###==> init AuthFilter...");
		MAPPING.put(RoleType.MANAGER, Arrays.asList(new String[] { "/users/", "/bank/", "/cart/" }));
		MAPPING.put(RoleType.ADMIN, Arrays.asList(new String[] { "/users/", "/bank/", "/cart/" }));
		MAPPING.put(RoleType.USER, Arrays.asList(new String[] { "/bank/"}));
	}

	/**
	 * @param authority: role of user such as ROLE_ADMIN
	 * @param requestURI such as "/api/cart/cart/products"
	 * @return
	 */
	public boolean validateRole(String authority, String requestURI) {
		final int NOT_FOUND = -1;
		LOGGER.info("###==> AuthFilter.validateRole() :: authority: " + authority + " - requestURI: " + requestURI);
		boolean isFound = false;
		RoleType role = RoleType.getRoleTypeByRolePrefix(authority);
		List<String> accessableAPIs = MAPPING.get(role);
		for (String accessableAPI : accessableAPIs) {
			isFound = requestURI.indexOf(accessableAPI) == NOT_FOUND ? false : true;
			if(isFound) {
				LOGGER.info("###==> AuthFilter.validateRole() :: Current user can access to: " + requestURI);
				break;
			}
		}

		return isFound;
	}
	
}
