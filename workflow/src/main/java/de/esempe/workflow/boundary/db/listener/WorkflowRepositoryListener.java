package de.esempe.workflow.boundary.db.listener;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.stereotype.Component;

import de.esempe.workflow.domain.Workflow;

@Component
public class WorkflowRepositoryListener extends AbstractMongoEventListener<Workflow>
{
	@Override
	public void onAfterConvert(final AfterConvertEvent<Workflow> event)
	{
		final Workflow workflow = event.getSource();
		workflow.populateStatesFromTransitions();
	}
}
