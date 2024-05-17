package todoapp.service;

import java.util.HashSet;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import todoapp.dto.LoginDto;
import todoapp.dto.RegisterDto;
import todoapp.exception.TodoAPIException;
import todoapp.model.Role;
import todoapp.model.User;
import todoapp.repository.RoleRepository;
import todoapp.repository.UserRepository;
import todoapp.security.JwtTokenProvider;
@Service
public class AuthServiceImpl  implements AuthService{
     
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder   passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Override
	public String register(RegisterDto registerDto) {
		
	// check username is alredy exist or not
	 if(userRepository.existsByUsername(registerDto.getUsername())) {
		throw new TodoAPIException(HttpStatus.BAD_REQUEST,"Username already exists!");  
	 }
	 // check email is already exists in db
	 if(userRepository.existsByEmail(registerDto.getEmail())) {
		 throw new TodoAPIException(HttpStatus.BAD_REQUEST,"Email is already exists!");
	 }
	 
	User user = new User();
	user.setName(registerDto.getName());
	user.setUsername(registerDto.getUsername());
	user.setEmail(registerDto.getEmail());
	user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
	
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER");
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);
		
		   
		return "User Registered Successfully.";
	}

	@Override
	public String login(LoginDto loginDto) {
		
  Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));
	
   SecurityContextHolder.getContext().setAuthentication(authenticate);
	
   String token = jwtTokenProvider.generateToken(authenticate); 
		return token;
	}

}
