package de.esempe.demo.domain;

import com.google.common.base.Preconditions;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "restdemo", name = "t_users")
@Access(AccessType.FIELD)
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String firstname;
	private String lastname;

	public User()
	{
		this.id = -1;
	}

	public int getId()
	{
		return this.id;
	}

	public void setId(final int id)
	{
		this.id = id;
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
