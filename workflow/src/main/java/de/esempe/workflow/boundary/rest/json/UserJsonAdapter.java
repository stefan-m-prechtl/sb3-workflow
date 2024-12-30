package de.esempe.workflow.boundary.rest.json;

import java.util.Collection;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import de.esempe.workflow.domain.DomainFactory;
import de.esempe.workflow.domain.GlobalRole;
import de.esempe.workflow.domain.User;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.bind.adapter.JsonbAdapter;

public class UserJsonAdapter implements JsonbAdapter<User, JsonObject>
{
	private static String FIELD_ID = "id";
	private static String FIELD_USERNAME = "name";
	private static String FIELD_PASSWD = "password";
	private static String FIELD_FIRSTNAME = "firstname";
	private static String FIELD_LASTNAME = "lastname";
	private static String FIELD_GLOBAL_ROLES = "globalroles";

	@Override
	public JsonObject adaptToJson(final User user) throws Exception
	{

		final var result = Json.createObjectBuilder() //
				.add(FIELD_ID, user.getId()) //
				.add(FIELD_USERNAME, user.getUsername())//
				.add(FIELD_FIRSTNAME, user.getFirstname())//
				.add(FIELD_LASTNAME, user.getLastname()) //
				.add(FIELD_GLOBAL_ROLES, this.adaptRolesToJson(user.getGlobalRoles()))//
				.build();

		return result;
	}

	private JsonArrayBuilder adaptRolesToJson(Collection<GlobalRole> globalRoles) throws Exception
	{
		final JsonArrayBuilder result = Json.createArrayBuilder();
		final GlobalRoleJsonAdapter adapter = new GlobalRoleJsonAdapter();

		for (final GlobalRole role : globalRoles)
		{
			result.add(adapter.adaptToJson(role));
		}

		return result;
	}

	@Override
	public User adaptFromJson(final JsonObject jsonObj) throws Exception
	{
		User result;
		final var username = jsonObj.getString(FIELD_USERNAME);
		final var password = jsonObj.getString(FIELD_PASSWD);
		final var firstname = jsonObj.getString(FIELD_FIRSTNAME);
		final var lastname = jsonObj.getString(FIELD_LASTNAME);

		if (jsonObj.containsKey(FIELD_ID))
		{
			final var id = jsonObj.getInt(FIELD_ID);
			result = DomainFactory.createUser(id, username);
		}
		else
		{
			result = DomainFactory.createUser(username);
		}
		result.setFirstname(firstname);
		result.setLastname(lastname);

		// Hash clear password
		final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		final String hashedPassword = passwordEncoder.encode(password);
		result.setHashedpwd(hashedPassword);

		return result;
	}
}
