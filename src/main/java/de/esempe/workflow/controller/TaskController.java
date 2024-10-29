package de.esempe.workflow.controller;

import com.google.common.base.Preconditions;

import de.esempe.workflow.domain.Workflow;
import de.esempe.workflow.domain.WorkflowTask;

public class TaskController
{
	private Workflow workflow;
	private WorkflowTask task;

	private TaskController()
	{

	}

	public static TaskController create(final Workflow workflow, final WorkflowTask task)
	{
		final var result = new TaskController();

		Preconditions.checkState(task.getWorkflowObjId().equals(workflow.getObjId()), "Workflow/Workflow-Task unterschiedlich");

		result.workflow = workflow;
		result.task = task;

		return result;

	}

	public void startTask()
	{
		Preconditions.checkState(this.task.getCurrentStateObjId().isEmpty(), "Task wurde bereits gestartet");
		this.task.setCurrentState(this.workflow.getStartState());

	}

}
