package de.esempe.workflow.boundary.db.listener;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import de.esempe.workflow.domain.WorkflowTask;

@Component
//onBeforeConvert: Before an entity is converted to a DBObject for saving.
//onBeforeSave: Before the entity is saved to MongoDB.
//onAfterSave: After the entity is saved to MongoDB.
//onAfterLoad: After an entity is loaded from MongoDB.
//onAfterConvert: After a DBObject is converted to an entity.
//onBeforeDelete: Before an entity is deleted.
//onAfterDelete: After an entity is deleted.
public class WorkflowTaskRepositoryListener extends AbstractMongoEventListener<WorkflowTask>
{
	@Override
	public void onBeforeConvert(final BeforeConvertEvent<WorkflowTask> event)
	{
	}

	@Override
	public void onAfterConvert(final AfterConvertEvent<WorkflowTask> event)
	{
//		final Document document = event.getDocument();
//		final String jsonDataString = (String) document.get(WorkflowTask.FIELD_NAME_JSONDATA);
//		final WorkflowTask task = event.getSource();
	}
}
