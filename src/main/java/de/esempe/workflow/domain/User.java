package de.esempe.workflow.domain;

import com.google.common.base.Preconditions;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(schema = "restdemo", name = "t_users")
@Access(AccessType.FIELD)
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull
	private String username;
	@NotNull
	private String firstname;
	@NotNull
	private String lastname;

	User()
	{
		this.id = -1;
	}

	public static User create(final int id, final String username)
	{
		final var result = new User();
		result.id = id;
		result.username = username;

		return result;
	}

	public static User create(final String username)
	{
		final var result = new User();
		result.username = username;
		return result;
	}

	public int getId()
	{
		return this.id;
	}

	public String getUsername()
	{
		return this.username;
	}

	public String getFirstname()
	{
		return this.firstname;
	}

	public void setFirstname(final String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return this.lastname;
	}

	public void setLastname(final String lastname)
	{
		Preconditions.checkNotNull(lastname);
		this.lastname = lastname;
	}
}
