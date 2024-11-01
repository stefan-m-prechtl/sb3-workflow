package de.esempe.workflow.boundary.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class TokenHandler
{
	private final static String KEY_USERID = "userid";
	private final static String KEY_USERNAME = "username";
	private final static String KEY_USERROLE = "userrole";

	// TODO : sichere Signatur
	private final static String SECRET = "secret";

	private TokenHandler()
	{
		// Keine Instant notwendig!
	}

	public static String createTokenFor(final String userid, final String username, final String userrole)
	{
		final Algorithm algorithm = Algorithm.HMAC256(SECRET);
		final Map<String, String> payloadClaims = new HashMap<>();
		payloadClaims.put(KEY_USERID, userid);
		payloadClaims.put(KEY_USERNAME, username);
		payloadClaims.put(KEY_USERROLE, userrole);

		// //@formatter:off
		final String token = JWT.create()
				.withIssuer("esempe")
				.withSubject("DEMO")
				.withPayload(payloadClaims)
				.sign(algorithm);
		//@formatter:on

		return token;
	}

	public static Optional<DecodedJWT> decodeToken(final String token)
	{
		Optional<DecodedJWT> result = Optional.empty();
		try
		{
			final Algorithm algorithm = Algorithm.HMAC256(SECRET);
			final JWTVerifier verifier = JWT.require(algorithm).withIssuer("esempe").withSubject("DEMO").build();
			final DecodedJWT jwt = verifier.verify(token);

			// TODO Token prüfen
			// jwt.getAudience();
			// jwt.getExpiresAt()
			// ...

			result = Optional.of(jwt);
		}
		catch (final IllegalArgumentException e)
		{
			System.out.println(e);
		}
		return result;

	}

	public static String getUseridFromToken(final DecodedJWT jwt)
	{
		return jwt.getClaim(KEY_USERID).asString();
	}

	public static String getUsernameFromToken(final DecodedJWT jwt)
	{
		return jwt.getClaim(KEY_USERNAME).asString();
	}

	public static String getUserroleFromToken(final DecodedJWT jwt)
	{
		return jwt.getClaim(KEY_USERROLE).asString();
	}

}
