package de.esempe.workflow.boundary.rest.security;

import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

class CustomAuthenticationToken extends AbstractAuthenticationToken
{
	private static final long serialVersionUID = 1L;

	private String username;

	CustomAuthenticationToken(final String name, final List<GrantedAuthority> authorities)
	{
		super(authorities);
		this.username = name;
		this.setAuthenticated(true);
	}

	@Override
	public Object getCredentials()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrincipal()
	{
		return this.username;
	}

}
