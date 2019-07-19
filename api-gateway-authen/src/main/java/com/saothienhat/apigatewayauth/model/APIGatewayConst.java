package com.saothienhat.apigatewayauth.model;

public final class APIGatewayConst {
	
	public static final int CRYPT_PASSWORD_ENCODER_STRENGTH = 12;
	public static final String  PREFIX_ROLE = "ROLE_";
	
	public final class JWT {
		public static final String TOKEN_PREFIX = "Bearer ";
		public static final String SECRET_KEY = "secret";
		public static final long VALIDITY_IN_MILLISECONDS = 3600000; // 1h
		
	}

}
