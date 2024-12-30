package de.esempe.workflow.boundary.rest.json;

import de.esempe.workflow.domain.GlobalRole;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.adapter.JsonbAdapter;

public class GlobalRoleJsonAdapter implements JsonbAdapter<GlobalRole, JsonObject>
{
	private static String FIELD_ID = "id";
	private static String FIELD_NAME = "name";
	private static String FIELD_DESCRIPTION = "description";

	@Override
	public JsonObject adaptToJson(GlobalRole role) throws Exception
	{
		final var result = Json.createObjectBuilder() //
				.add(FIELD_ID, role.getId()) //
				.add(FIELD_NAME, role.getRoleName()) //
				.add(FIELD_DESCRIPTION, role.getDescription()) //
				.build();

		return result;
	}

	@Override
	public GlobalRole adaptFromJson(JsonObject jsonObj) throws Exception
	{
		throw new UnsupportedOperationException("GlobalRoleJsonAdapter::adaptFromJson() is not supported");
	}

}
