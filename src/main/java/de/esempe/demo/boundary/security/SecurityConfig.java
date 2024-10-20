package de.esempe.demo.boundary.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
	@Bean
	SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception
	{
		http //
				.authorizeHttpRequests(authorize -> authorize //
						.requestMatchers("/ping").permitAll() //
						.requestMatchers("/user").authenticated() //
						.anyRequest().authenticated() //
				) //
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //
				.csrf(csrf -> csrf.disable());

		http.addFilterBefore(this.jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	JwtRequestFilter jwtRequestFilter()
	{
		return new JwtRequestFilter();
	}

}
