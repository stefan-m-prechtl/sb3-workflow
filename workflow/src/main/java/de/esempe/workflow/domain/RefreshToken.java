package de.esempe.workflow.domain;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "restdemo", name = "t_refreshtokens")
@Access(AccessType.FIELD)
public class RefreshToken
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "userid", referencedColumnName = "id")
	private User user;

	@Column(name = "token")
	private String token;

	@Column(name = "expirydate")
	private Instant expiryDate;

	RefreshToken()
	{
		this.id = -1L;
	}

	public RefreshToken create(final User user)
	{
		final var result = new RefreshToken();
		result.user = user;
		result.token = UUID.randomUUID().toString();
		result.expiryDate = Instant.now().plus(24, ChronoUnit.HOURS);

		return result;
	}

	public boolean isValid()
	{
		return Instant.now().isBefore(this.expiryDate);
	}

	public Long getId()
	{
		return this.id;
	}

	public User getUser()
	{
		return this.user;
	}

	public String getToken()
	{
		return this.token;
	}

	public Instant getExpiryDate()
	{
		return this.expiryDate;
	}

}
