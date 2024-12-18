package de.esempe.workflow.boundary.rest.security;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
class JwtRequestFilter extends OncePerRequestFilter
{
	private static final String AUTHORIZATION_ERROR = "Authorization-Error";

	@Autowired
	TokenHandler tokenHandler;

	private record TokenContent(boolean valid, String username, List<String> userroles)
	{

	}

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws ServletException, IOException
	{
		final String token = this.extractTokenFromRequest(request).orElse("");
		if (!token.isEmpty())
		{
			final TokenContent tokencontent = this.validateToken(token);
			if (tokencontent.valid)
			{

				final String name = tokencontent.username;
				final List<GrantedAuthority> authorities = tokencontent.userroles //
						.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

				final var authentication = new CustomAuthenticationToken(name, authorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			else
			{
				response.addHeader(AUTHORIZATION_ERROR, "JWT is invalid");
			}
		}
		else
		{
			response.addHeader(AUTHORIZATION_ERROR, "Bearer/Token is missing");
		}

		chain.doFilter(request, response);

	}

	private Optional<String> extractTokenFromRequest(final HttpServletRequest request)
	{
		final var token = request.getHeader("Authorization");
		if ((token != null) && token.startsWith("Bearer "))
		{
			return Optional.of(token.substring(7));
		}
		return Optional.empty();
	}

	private TokenContent validateToken(final String token)
	{
		final Optional<DecodedJWT> optionalToken = this.tokenHandler.decodeToken(token);
		if (optionalToken.isEmpty())
		{
			return new TokenContent(false, "", List.of());
		}

		final DecodedJWT jwt = optionalToken.get();
		// Aktuelle Werte für Benutzer und Rolle aus Token extrahieren
		final String username = this.tokenHandler.getUsernameFromToken(jwt);
		final List<String> userroles = this.tokenHandler.getUserroleFromToken(jwt);

		return new TokenContent(true, username, userroles);
	}

}
