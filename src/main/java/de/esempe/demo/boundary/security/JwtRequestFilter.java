package de.esempe.demo.boundary.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Strings;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtRequestFilter extends OncePerRequestFilter
{
	private static final String AUTHORIZATION_ERROR = "Authorization-Error";

	private record TokenContent(boolean valid, String username, String userrole)
	{

	}

	@Autowired
	private TokenHandler jwtUtil;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws ServletException, IOException
	{
		String authorization = request.getHeader("Authorization");
		if (Strings.isNullOrEmpty(authorization))
		{
			response.addHeader(AUTHORIZATION_ERROR, "Authorization is missing");
		}
		else
		{
			authorization = authorization.strip();
			if (authorization.startsWith("Bearer", 0) && authorization.contains(" "))
			{
				// final String token = authorization.split(" ")[1];
				final String token = TokenHandler.createTokenFor("prs", "Stefan Prechtl", "READER");

				final TokenContent tokencontent = this.validateToken(token);

				if (tokencontent.valid)
				{

					final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(tokencontent.username, tokencontent.username, new ArrayList<>());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
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
		}
		chain.doFilter(request, response);

	}

	private TokenContent validateToken(final String token)
	{
		final Optional<DecodedJWT> optionalToken = TokenHandler.decodeToken(token);
		if (optionalToken.isEmpty())
		{
			return new TokenContent(false, "", "");
		}

		final DecodedJWT jwt = optionalToken.get();
		// Aktuelle Werte für Benutzer und Rolle aus Token extrahieren
		final String username = TokenHandler.getUsernameFromToken(jwt);
		final String userrole = TokenHandler.getUserroleFromToken(jwt);

		return new TokenContent(true, username, userrole);
	}
}
