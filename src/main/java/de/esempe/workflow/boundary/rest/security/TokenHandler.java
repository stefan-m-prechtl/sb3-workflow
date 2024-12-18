package de.esempe.workflow.boundary.rest.security;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class TokenHandler
{
	private final static String KEY_USERNAME = "username";
	private final static String KEY_USERROLES = "userroles";

	@Autowired
	JwtProperties properties;

	TokenHandler()
	{

	}

	public String createTokenFor(final String username, final List<String> userroles)
	{
		final Algorithm algorithm = Algorithm.HMAC256(this.properties.getSecretKey());
		final Instant now = Instant.now();

		final String token = JWT.create() //
				.withSubject(username)//
				.withIssuer("esempe")//
				.withExpiresAt(now.plus(this.properties.getTokenDuration())) //
				.withClaim(TokenHandler.KEY_USERNAME, username) //
				.withClaim(TokenHandler.KEY_USERROLES, userroles) //
				.sign(algorithm);

		return token;
	}

	Optional<DecodedJWT> decodeToken(final String token)
	{
		Optional<DecodedJWT> result = Optional.empty();
		try
		{
			final Algorithm algorithm = Algorithm.HMAC256(this.properties.getSecretKey());
			final JWTVerifier verifier = JWT.require(algorithm).withIssuer("esempe").build();
			final DecodedJWT jwt = verifier.verify(token);

			result = Optional.of(jwt);
		}
		catch (final Exception e)
		{
			System.out.println(e);
		}
		return result;

	}

	String getUseridFromToken(final DecodedJWT jwt)
	{
		return jwt.getSubject();
	}

	String getUsernameFromToken(final DecodedJWT jwt)
	{
		return jwt.getClaim(KEY_USERNAME).asString();
	}

	List<String> getUserroleFromToken(final DecodedJWT jwt)
	{
		return jwt.getClaim(KEY_USERROLES).asList(String.class);
	}

}
