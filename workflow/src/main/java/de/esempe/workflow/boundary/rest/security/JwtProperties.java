package de.esempe.workflow.boundary.rest.security;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("security.jwt")
public class JwtProperties
{
	private String secretKey;
	private Duration tokenDuration;

	public String getSecretKey()
	{
		return this.secretKey;
	}

	public void setSecretKey(final String secretKey)
	{
		this.secretKey = secretKey;
	}

	public Duration getTokenDuration()
	{
		return this.tokenDuration;
	}

	public void setTokenDuration(final Duration tokenDuration)
	{
		this.tokenDuration = tokenDuration;
	}

}
