package de.esempe.demo.domain;

import com.google.common.base.Preconditions;

public class User
{
	private String firstname;
	private String lastname;

	public User()
	{

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
