package de.esempe.workflow.boundary.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig
{
	@Autowired
	UnauthorizedHandler unauthorizedHandler;

	@Autowired
	CustomUserDetailsService customUserDetailService;

	@Autowired
	JwtRequestFilter jwtRequestFilter;

	@Bean
	GrantedAuthorityDefaults grantedAuthorityDefaults()
	{
		return new GrantedAuthorityDefaults(""); // Remove ROLE_ prefix
	}

	@Bean
	SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception
	{
		http.addFilterBefore(this.jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		http //
				.cors(AbstractHttpConfigurer::disable) //
				.csrf(AbstractHttpConfigurer::disable) //
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //
				.formLogin(AbstractHttpConfigurer::disable) //
				.exceptionHandling(handler -> handler.authenticationEntryPoint(this.unauthorizedHandler)) //
				.securityMatcher("/**") //
				.authorizeHttpRequests(authorize -> authorize //
						.requestMatchers("/ping/reader").authenticated() //
						.requestMatchers("/ping/writer").authenticated() //
						.requestMatchers("/ping/admin").authenticated() //
						// .requestMatchers("/user").authenticated() //
						// während Entwicklung: keine Autorisierung
						.anyRequest().permitAll() //
				);

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(final HttpSecurity http) throws Exception
	{
		final AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		builder //
				.userDetailsService(this.customUserDetailService) //
				.passwordEncoder(this.passwordEncoder());

		final AuthenticationManager result = builder.build();
		return result;
	}

}
