package de.esempe.workflow.boundary.rest.json;

import java.util.UUID;

import de.esempe.workflow.domain.WorkflowRule;
import de.esempe.workflow.domain.WorkflowState;
import de.esempe.workflow.domain.WorkflowTransition;
import de.esempe.workflow.domain.WorkflowTransition.TransistionType;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.adapter.JsonbAdapter;

public class WorkflowTransitionJsonAdapter implements JsonbAdapter<WorkflowTransition, JsonObject>
{
	private static String FIELD_ID = "id";
	private static String FIELD_NAME = "name";
	private static String FIELD_FROM_STATE = "fromState";
	private static String FIELD_TO_STATE = "toState";
	private static String FIELD_TYPE = "type";
	private static String FIELD_RULE = "rule";

	@Override
	public JsonObject adaptToJson(final WorkflowTransition transition) throws Exception
	{
		final WorkflowStateJsonAdapter stateAdpater = new WorkflowStateJsonAdapter();
		final WorkflowRuleJsonAdapter ruleAdapter = new WorkflowRuleJsonAdapter();

		final var result = Json.createObjectBuilder() //
				.add(FIELD_ID, transition.getObjId().toString()) //
				.add(FIELD_NAME, transition.getName())//
				.add(FIELD_FROM_STATE, stateAdpater.adaptToJson(transition.getFromState()))//
				.add(FIELD_TO_STATE, stateAdpater.adaptToJson(transition.getToState()))//
				.add(FIELD_RULE, ruleAdapter.adaptToJson(transition.getRule()))//
				.add(FIELD_TYPE, transition.getType().toString())//

				.build();

		return result;
	}

	@Override
	public WorkflowTransition adaptFromJson(final JsonObject jsonObj) throws Exception
	{
		WorkflowTransition result = null;
		final WorkflowStateJsonAdapter stateAdpater = new WorkflowStateJsonAdapter();
		final WorkflowRuleJsonAdapter ruleAdapter = new WorkflowRuleJsonAdapter();

		final String name = jsonObj.getString(FIELD_NAME);
		final WorkflowState fromState = stateAdpater.adaptFromJson((JsonObject) jsonObj.get(FIELD_FROM_STATE));
		final WorkflowState toState = stateAdpater.adaptFromJson((JsonObject) jsonObj.get(FIELD_TO_STATE));
		final TransistionType type = TransistionType.valueOf(jsonObj.getString(FIELD_TYPE));
		final WorkflowRule rule = ruleAdapter.adaptFromJson((JsonObject) jsonObj.get(FIELD_RULE));

		if (jsonObj.containsKey(FIELD_ID))
		{
			final UUID objId = UUID.fromString(jsonObj.getString(FIELD_ID));
			result = WorkflowTransition.create(objId, name, fromState, toState);
		}
		else
		{
			result = WorkflowTransition.create(name, fromState, toState);
		}
		result.setType(type);
		result.setRule(rule);

		return result;
	}

}
