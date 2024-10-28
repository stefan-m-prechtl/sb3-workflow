package de.esempe.workflow.boundary;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import de.esempe.workflow.domain.WorkflowState;

@Component
public class WorkflowStateRepositoryListener extends AbstractMongoEventListener<WorkflowState>
{
	@Override
	public void onBeforeConvert(final BeforeConvertEvent<WorkflowState> event)
	{
		final WorkflowState state = event.getSource();

		if (state.getJsonData().isPresent())
		{
			state.setData(state.getJsonData().get().toString());
		}
	}

//	@Override
//	public void onBeforeSave(final BeforeSaveEvent<WorkflowState> event)
//	{
//		final WorkflowState state = event.getSource();
//
//		if (state.getJsonData().isPresent())
//		{
//			state.setData(state.getJsonData().get().toString());
//		}
//	}

	@Override
	public void onAfterSave(final AfterSaveEvent<WorkflowState> event)
	{
		final WorkflowState state = event.getSource();
		System.out.println("onAfterSave");
	}
}
