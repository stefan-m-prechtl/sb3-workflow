package de.esempe.workflow.boundary.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.esempe.workflow.boundary.LoginData;
import de.esempe.workflow.boundary.rest.security.TokenHandler;

@RestController
@RequestMapping("auth")
public class AuthentificationResource
{
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	TokenHandler tokenHandler;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody @Validated final LoginData data)
	{
		final Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.getUser(), data.getPasswd()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		final UserDetails user = (UserDetails) authentication.getPrincipal();
		final String name = user.getUsername();
		final List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

		final String token = this.tokenHandler.createTokenFor(name, roles);
		final var result = ResponseEntity.status(HttpStatus.OK).body(token);
		return result;
	}
}
