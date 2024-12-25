package de.esempe.workflow.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(schema = "restdemo", name = "t_globalroles")
@Access(AccessType.FIELD)
public class GlobalRole
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "rolename")
	@NotNull
	private String roleName;
	@NotNull
	private String description;

	GlobalRole()
	{
		id = -1;
	}

	public static GlobalRole create(final int id, final String roleName)
	{
		final var result = new GlobalRole();
		result.id = id;
		result.roleName = roleName;

		return result;
	}

	public static GlobalRole create(final String roleName)
	{
		final var result = new GlobalRole();
		result.roleName = roleName;
		return result;
	}

	public Integer getId()
	{
		return id;
	}

	public String getRoleName()
	{
		return roleName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

}
