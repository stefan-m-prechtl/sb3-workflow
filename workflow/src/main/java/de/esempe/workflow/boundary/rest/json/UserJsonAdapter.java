package de.esempe.workflow.boundary.rest.json;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import de.esempe.workflow.domain.User;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.adapter.JsonbAdapter;

public class UserJsonAdapter implements JsonbAdapter<User, JsonObject>
{
	private static String FIELD_ID = "user-id";
	private static String FIELD_USERNAME = "user-name";
	private static String FIELD_PASSWD = "user-password";
	private static String FIELD_FIRSTNAME = "user-firstname";
	private static String FIELD_LASTNAME = "user-lastname";

	@Override
	public JsonObject adaptToJson(final User user) throws Exception
	{
		final var result = Json.createObjectBuilder() //
				.add(FIELD_ID, user.getId()) //
				.add(FIELD_FIRSTNAME, user.getUsername())//
				.add(FIELD_FIRSTNAME, user.getFirstname())//
				.add(FIELD_LASTNAME, user.getLastname()) //
				.build();

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
			result = User.create(id, username);
		}
		else
		{
			result = User.create(username);
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
