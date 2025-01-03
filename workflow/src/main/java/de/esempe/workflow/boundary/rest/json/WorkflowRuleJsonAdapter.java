package de.esempe.workflow.boundary.rest.json;

import java.util.UUID;

import de.esempe.workflow.domain.WorkflowRule;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.adapter.JsonbAdapter;

public class WorkflowRuleJsonAdapter implements JsonbAdapter<WorkflowRule, JsonObject>
{
	private static String FIELD_ID = "id";
	private static String FIELD_NAME = "name";
	private static String FIELD_SCRIPT = "script";

	@Override
	public JsonObject adaptToJson(final WorkflowRule rule) throws Exception
	{
		if (null == rule)
		{
			return Json.createObjectBuilder().build();
		}

		final var result = Json.createObjectBuilder() //
				.add(FIELD_ID, rule.getObjId().toString()) //
				.add(FIELD_NAME, rule.getName())//
				.add(FIELD_SCRIPT, rule.getScript())//
				.build();

		return result;
	}

	@Override
	public WorkflowRule adaptFromJson(final JsonObject jsonObj) throws Exception
	{
		WorkflowRule result = null;

		final String name = jsonObj.getString(FIELD_NAME);
		final String script = jsonObj.getString(FIELD_SCRIPT);

		if (jsonObj.containsKey(FIELD_ID))
		{
			final UUID objId = UUID.fromString(jsonObj.getString(FIELD_ID));
			result = WorkflowRule.create(objId, name, script);
		}
		else
		{
			result = WorkflowRule.create(name, script);
		}

		return result;
	}

}
