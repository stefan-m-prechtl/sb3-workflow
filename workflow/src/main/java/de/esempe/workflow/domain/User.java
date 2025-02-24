package de.esempe.workflow.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.google.common.base.Preconditions;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(schema = "restdemo", name = "t_users")
@Access(AccessType.FIELD)
public class User extends AbstractEntity
{
	@NotNull
	private String username;
	@NotNull
	private String hashedpwd;
	@NotNull
	private String firstname;
	@NotNull
	private String lastname;

	@ManyToMany
	@JoinTable(schema = "restdemo", name = "t_users2globalroles", //
			joinColumns = @JoinColumn(name = "userid"), // FK User
			inverseJoinColumns = @JoinColumn(name = "roleid") // FK GlobalRole
	)
	private Set<GlobalRole> globalRoles;

	User()
	{
		super();
	}

	static User create(final long id, final String username)
	{
		final var result = new User();
		result.id = id;
		result.username = username;
		result.hashedpwd = "";

		return result;
	}

	static User create(final String username)
	{
		final var result = new User();
		result.username = username;
		result.hashedpwd = "";
		return result;
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

	public String getHashedpwd()
	{
		return this.hashedpwd;
	}

	public void setHashedpwd(final String hashedpwd)
	{
		this.hashedpwd = hashedpwd;
	}

	public Collection<GlobalRole> getGlobalRoles()
	{
		return Collections.unmodifiableSet(this.globalRoles);
	}

}
