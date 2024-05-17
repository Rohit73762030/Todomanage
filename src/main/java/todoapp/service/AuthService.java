package todoapp.service;

import org.springframework.stereotype.Service;

import todoapp.dto.LoginDto;
import todoapp.dto.RegisterDto;

@Service
public interface AuthService {
	String register(RegisterDto registerDto);
	
	String login(LoginDto loginDto );

}
