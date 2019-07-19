package com.saothienhat.apigatewayauth.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Getter
//@Setter
public class LoginRequest implements Serializable {
	
	private static final long serialVersionUID = -6986746375915710855L;

//public class LoginRequest {
//	@Getter
//	@Setter
    private String username;
//	@Getter
//	@Setter
	private String password;

//    public LoginRequest() {
//    }
//
//    public LoginRequest(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
//
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
