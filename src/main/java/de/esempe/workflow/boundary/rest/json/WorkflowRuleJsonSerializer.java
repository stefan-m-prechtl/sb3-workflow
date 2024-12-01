package de.esempe.workflow.boundary.rest.json;

import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflowRule.FIELD_NAME;
import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflowRule.FIELD_SCRIPT;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import de.esempe.workflow.domain.WorkflowRule;

@JsonComponent
public class WorkflowRuleJsonSerializer extends JsonSerializer<WorkflowRule>
{
	@Override
	public void serialize(final WorkflowRule rule, final JsonGenerator generator, final SerializerProvider serializers) throws IOException
	{
		generator.writeStartObject();
		generator.writeStringField(FIELD_NAME, rule.getName());
		generator.writeStringField(FIELD_SCRIPT, rule.getScript());
		generator.writeEndObject();
	}
}
