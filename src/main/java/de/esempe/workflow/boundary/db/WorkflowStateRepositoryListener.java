package de.esempe.workflow.boundary.db;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import de.esempe.workflow.domain.WorkflowState;

@Component
public class WorkflowStateRepositoryListener extends AbstractMongoEventListener<WorkflowState>
{
	@Override
	public void onBeforeConvert(final BeforeConvertEvent<WorkflowState> event)
	{
		final WorkflowState state = event.getSource();
		System.out.println("onBeforeConvert:" + state.getName());
	}

	@Override
	public void onBeforeSave(final BeforeSaveEvent<WorkflowState> event)
	{
		final WorkflowState state = event.getSource();
		System.out.println("onBeforeSave: " + state.getName());
	}

	@Override
	public void onAfterSave(final AfterSaveEvent<WorkflowState> event)
	{
		final WorkflowState state = event.getSource();
		System.out.println("onAfterSave: " + state.getName());
	}
}
