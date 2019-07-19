package com.saothienhat.apigatewayauth.security;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saothienhat.apigatewayauth.exception.CustomException;
import com.saothienhat.apigatewayauth.model.APIGatewayConst;
import com.saothienhat.apigatewayauth.model.MongoUserDetails;
import com.saothienhat.apigatewayauth.model.PostgresUserDetails;
import com.saothienhat.apigatewayauth.model.Role;
import com.saothienhat.apigatewayauth.model.RoleType;
import com.saothienhat.apigatewayauth.model.User;

@Service
public class UserService implements UserDetailsService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
//    @Autowired
//    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	LOGGER.info("###==> loading user by username: " + email);
        User user = findByName(email) ; // userRepository.findByEmail(email);
        if (user == null || user.getRole() == null || user.getRole().isEmpty()) {
            throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
        }
        String [] authorities = new String[user.getRole().size()];
        int count=0;
        for (Role role : user.getRole()) {
            //NOTE: normally we dont need to add "ROLE_" prefix. Spring does automatically for us.
            //Since we are using custom token using JWT we should add ROLE_ prefix
			authorities[count] = APIGatewayConst.PREFIX_ROLE + role.getRole();
            count++;
        }
        
		PostgresUserDetails userDetails = new PostgresUserDetails(user.getUsername(), user.getPassword(), user.getActive(),
				user.isLoacked(), user.isExpired(), user.isEnabled(), authorities);
//        PostgresUserDetails userDetails = new PostgresUserDetails();
//        userDetails.setPassword(user.getPassword());
//        userDetails.setUsername(user.getUsername());
		
		// FIXME
		String password = "123";
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(APIGatewayConst.CRYPT_PASSWORD_ENCODER_STRENGTH);
		LOGGER.info("============> loadUserByUsername(): " + passwordEncoder.matches(password, userDetails.getPassword()));		
		String hashedPassword = passwordEncoder.encode(password);
		userDetails.setPassword(hashedPassword);
		LOGGER.info("============> loadUserByUsername(): " + passwordEncoder.matches(password, userDetails.getPassword()));	
        
        return userDetails;
    }
    
//    private User findByName(String username) {
//		User user = getUser(username);
//		if(username != null && !username.isEmpty() && "binh".equalsIgnoreCase(username)) {
//			Set<Role> roles = new HashSet<Role>();
//			roles.add(new Role(1, RoleType.ADMIN.getRoleName()));
//			user = new User();
//			user.setUsername(username);
//			user.setLastName("Binh Trinh");
//			user.setName("Binh Trinh");
//			user.setPassword("123");
//			user.setRole(roles);
//			
//			roles = new HashSet<Role>();
//			roles.add(new Role(1, RoleType.USER.getRoleName()));
//			user = new User();
//			user.setUsername(username);
//			user.setLastName("Binh Trinh");
//			user.setName("Binh Trinh");
//			user.setPassword("123");
//			user.setRole(roles);
//		}
//		return user;
//	}

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



}
