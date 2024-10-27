package de.esempe.demo.domain;

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
	private String firstname;
	@NotNull
	private String lastname;

	public User()
	{
		this.id = -1;
	}

	// Für Json-Adapter
	public static User createWithId(final int id, final String firstname, final String lastname)
	{
		final var result = new User();
		result.id = id;
		result.firstname = firstname;
		result.lastname = lastname;
		return result;
	}

	public int getId()
	{
		return this.id;
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
