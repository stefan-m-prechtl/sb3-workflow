package de.esempe.workflow.boundary.rest.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.esempe.workflow.controller.UserService;
import de.esempe.workflow.domain.User;

@Service
class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
	private UserService service;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException
	{
		final User user = this.service.loadUserByUsername(username).orElseThrow();

		final List<GrantedAuthority> authorities = List.of("READER") // user.getRoles()
				.stream().map(SimpleGrantedAuthority::new) // Assuming roles are stored as strings
				.collect(Collectors.toList());

		final UserDetails result = org.springframework.security.core.userdetails.User.builder() //
				.username(user.getUsername()) //
				.password(user.getHashedpwd())//
				.authorities(authorities) //
				.accountExpired(false) //
				.accountLocked(false) //
				.credentialsExpired(false) //
				.disabled(false) //
				.build();

		return result;
	}

}
