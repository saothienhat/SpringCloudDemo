package com.saothienhat.apigatewayauth.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saothienhat.apigatewayauth.exception.CustomException;
import com.saothienhat.apigatewayauth.model.Role;
import com.saothienhat.apigatewayauth.model.RoleType;
import com.saothienhat.apigatewayauth.model.User;
import com.saothienhat.apigatewayauth.security.JwtTokenProvider;

@Service
public class LoginService implements ILoginService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private JwtTokenRepository jwtTokenRepository;

    @Override
	public String login(String username, String password) {
		LOGGER.info("###==> loging ........ ");
		try {

			// This action will check authentication of porperties of UserDetail
			// (PostgresUserDetail in this case) such as isExpired, etc
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			User user = findByName(username);// userRepository.findByEmail(username); // FIXME
			if (user == null || user.getRole() == null || user.getRole().isEmpty()) {
				throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
			}
			// NOTE: normally we dont need to add "ROLE_" prefix. Spring does automatically
			// for us.
			// Since we are using custom token using JWT we should add ROLE_ prefix
			String token = jwtTokenProvider.createToken(username,
					user.getRole().stream().map((Role role) -> "ROLE_" + role.getRole()).filter(Objects::nonNull)
							.collect(Collectors.toList()));
			LOGGER.info("###==> token after login: " + token);
			return token;

		} catch (AuthenticationException e) {
			LOGGER.error("===> e: " + e.getMessage());
			throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
		}
	}

    private User findByName(String username) {
		User user = new User();
		if(username != null && !username.isEmpty() && "binh".equalsIgnoreCase(username)) {
			Set<Role> roles = new HashSet<Role>();
			roles.add(new Role(1, RoleType.ADMIN.getRoleName()));
			user = new User();
			user.setUsername(username);
			user.setLastName("Binh Trinh");
			user.setName("Binh Trinh");
			user.setPassword("123");
			user.setRole(roles);
		} else if(username != null && !username.isEmpty() && "minh".equalsIgnoreCase(username)) {
			Set<Role> roles = new HashSet<Role>();
			roles.add(new Role(1, RoleType.USER.getRoleName()));
			user = new User();
			user.setUsername(username);
			user.setLastName("Minh Vu");
			user.setName("Minh Vu");
			user.setPassword("123");
			user.setRole(roles);
		}
		return user;
    }



	@Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()) );
//        return userRepository.save(user); // FIXME
        return user;
    }

    @Override
    public boolean logout(String token) {
//         jwtTokenRepository.delete(new JwtToken(token)); // FIXME
         return true;
    }

    @Override
    public Boolean isValidToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @Override
    public String createNewToken(String token) {
        String username = jwtTokenProvider.getUsername(token);
        List<String>roleList = jwtTokenProvider.getRoleListByToken(token);
        String newToken =  jwtTokenProvider.createToken(username,roleList);
        return newToken;
    }
}
