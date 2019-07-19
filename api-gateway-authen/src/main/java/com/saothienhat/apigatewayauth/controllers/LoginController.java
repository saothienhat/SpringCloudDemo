package com.saothienhat.apigatewayauth.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saothienhat.apigatewayauth.model.APIGatewayConst;
import com.saothienhat.apigatewayauth.model.AuthResponse;
import com.saothienhat.apigatewayauth.model.LoginRequest;
import com.saothienhat.apigatewayauth.services.ILoginService;

@Controller
@RequestMapping("/api")
public class LoginController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private ILoginService iLoginService;

    @CrossOrigin("*")
    @PostMapping("/signin")
    @ResponseBody
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
//    public ResponseEntity signin(@RequestBody LoginRequest loginRequest) {
    	LOGGER.info("####==> login.......");  
        String token = iLoginService.login(loginRequest.getUsername(), loginRequest.getPassword()); 
        HttpHeaders headers = new HttpHeaders();
        List<String> headerlist = new ArrayList<>();
        List<String> exposeList = new ArrayList<>();
        headerlist.add("Content-Type");
        headerlist.add(" Accept");
        headerlist.add("X-Requested-With");
        headerlist.add("Authorization");
        headers.setAccessControlAllowHeaders(headerlist);
        exposeList.add("Authorization");
        headers.setAccessControlExposeHeaders(exposeList);
        headers.set("Authorization", token);
        LOGGER.info("####==> login DONE => token: " + token);
        return new ResponseEntity<AuthResponse>(new AuthResponse(token), headers, HttpStatus.CREATED);
        
//        Map<Object, Object> model = new HashMap<>();
//        model.put("headers", headers);
//        model.put("username", loginRequest.getUsername());
//        model.put("token", token);
//        return ok(model);
    }
    
    
    @CrossOrigin("*")
    @PostMapping("/signout")
    @ResponseBody
    public ResponseEntity<AuthResponse> logout (@RequestHeader(value="Authorization") String token) {
    	LOGGER.info("####==> logout() ............");  
        HttpHeaders headers = new HttpHeaders();
      if (iLoginService.logout(token)) {
          headers.remove("Authorization");
          return new ResponseEntity<AuthResponse>(new AuthResponse("logged out"), headers, HttpStatus.CREATED);
      }
        return new ResponseEntity<AuthResponse>(new AuthResponse("Logout Failed"), headers, HttpStatus.NOT_MODIFIED);
    }

    /**
     *
     * @param token
     * @return boolean.
     * if request reach here it means it is a valid token.
     */
    @PostMapping("/valid/token")
    @ResponseBody
    public Boolean isValidToken (@RequestHeader(value="Authorization") String token) {
    	LOGGER.info("####==> isValidToken() ............");
        return true;
    }


    @PostMapping("/signin/token")
    @CrossOrigin("*")
    @ResponseBody
    public ResponseEntity<AuthResponse> createNewToken (@RequestHeader(value="Authorization") String token) {
    	LOGGER.info("####==> createNewToken() ............");
        String newToken = iLoginService.createNewToken(token);
        HttpHeaders headers = new HttpHeaders();
        List<String> headerList = new ArrayList<>();
        List<String> exposeList = new ArrayList<>();
        headerList.add("Content-Type");
        headerList.add(" Accept");
        headerList.add("X-Requested-With");
        headerList.add("Authorization");
        headers.setAccessControlAllowHeaders(headerList);
        exposeList.add("Authorization");
        headers.setAccessControlExposeHeaders(exposeList);
        headers.set("Authorization", newToken);
        return new ResponseEntity<AuthResponse>(new AuthResponse(newToken), headers, HttpStatus.CREATED);
    }
    
    @PostMapping("/sign-up")
    public void signUp(@RequestBody LoginRequest user) {
    	LOGGER.info("####==> signUp() ............");
    	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(APIGatewayConst.CRYPT_PASSWORD_ENCODER_STRENGTH);
    	//  All passwords must be encoded.
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // applicationUserRepository.save(user);
    }
}
