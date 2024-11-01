package de.esempe.workflow.boundary;

import org.bson.Document;
import org.bson.json.JsonObject;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterLoadEvent;
import org.springframework.stereotype.Component;

import de.esempe.workflow.domain.WorkflowTask;

@Component
public class WorkflowTaskRepositoryListener extends AbstractMongoEventListener<WorkflowTask>
{
	@Override
	public void onAfterLoad(final AfterLoadEvent<WorkflowTask> event)
	{
		final Document source = event.getSource();
		final Document data = (Document) source.get(WorkflowTask.FIELD_NAME_JSONDATA);
		final String json = (String) data.get("json");
		final JsonObject jsonObj = new JsonObject(json);
		source.put(WorkflowTask.FIELD_NAME_JSONDATA, jsonObj);

	}
}
