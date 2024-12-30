package de.esempe.workflow.domain;

public final class DomainFactory
{
	private DomainFactory()
	{
	}

	public static User createUser(final int id, final String username)
	{
		final var result = User.create(id, username);
		return result;
	}

	public static User createUser(final String username)
	{
		final var result = User.create(username);
		return result;
	}

	public static GlobalRole createGlobalRole(String rolename)
	{
		final var result = GlobalRole.create(rolename);
		return result;
	}

}
