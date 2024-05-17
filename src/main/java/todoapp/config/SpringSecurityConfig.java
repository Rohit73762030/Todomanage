package todoapp.config;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import todoapp.security.JwtAuthenticationEntryPoint;
import todoapp.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@SuppressWarnings("removal")
	@Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	   http.csrf().disable()
	   .authorizeHttpRequests((authorize) -> {
//		   authorize.requestMatchers(HttpMethod.POST,"/api/**").hasRole("ADMIN");
//		   authorize.requestMatchers(HttpMethod.PUT,"/api/**").hasRole("ADMIN");
//		   authorize.requestMatchers(HttpMethod.DELETE,"/api/**").hasRole("ADMIN");
//		   authorize.requestMatchers(HttpMethod.GET,"/api/**").hasAnyRole("ADMIN","USER");
//		   authorize.requestMatchers(HttpMethod.PATCH,"/api/**").hasAnyRole("ADMIN","USER");
//		   authorize.requestMatchers(HttpMethod.GET,"api/**").permitAll();
		   authorize.requestMatchers("/api/auth/**").permitAll();
		   authorize.anyRequest().authenticated();
	   }).httpBasic(Customizer.withDefaults());
	   http.exceptionHandling(exception-> exception.authenticationEntryPoint(authenticationEntryPoint));
		
	   
	  http.addFilterBefore(authenticationFilter,
	    UsernamePasswordAuthenticationFilter.class); 
		return http.build();	
}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
		
	}
	
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails rohit =User.builder()
//				.username("rohit")
//				.password(passwordEncoder().encode("password"))
//				.roles("USER")
//				.build();
//		
//		UserDetails admin = User.builder()
//				.username("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles("ADMIN")
//				.build();
//		
//		return new InMemoryUserDetailsManager(rohit ,admin);
//		
//	}


}
