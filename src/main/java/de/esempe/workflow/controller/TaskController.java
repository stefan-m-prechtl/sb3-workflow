package de.esempe.workflow.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import com.google.common.base.Preconditions;

import de.esempe.workflow.domain.Workflow;
import de.esempe.workflow.domain.WorkflowRule;
import de.esempe.workflow.domain.WorkflowState;
import de.esempe.workflow.domain.WorkflowTask;
import de.esempe.workflow.domain.WorkflowTransition;
import de.esempe.workflow.domain.WorkflowTransition.TransistionType;

public class TaskController
{
	private Workflow workflow;
	private WorkflowTask task;
	private ScriptBuilder scriptBuilder;

	private TaskController()
	{
		this.scriptBuilder = new ScriptBuilder();
	}

	public static TaskController create(final Workflow workflow, final WorkflowTask task)
	{
		Preconditions.checkState(task.getWorkflowObjId().equals(workflow.getObjId()), "Workflow/Workflow-Task unterschiedlich");

		final var result = new TaskController();
		result.workflow = workflow;
		result.task = task;

		return result;
	}

	public void startTask()
	{
		Preconditions.checkState(this.task.getCurrentStateObjId().isEmpty(), "Task wurde bereits gestartet");
		this.setState(this.workflow.getStartState());
	}

	private void setState(final WorkflowState state)
	{
		this.task.setCurrentStateObjId(state.getObjId());

		if (this.workflow.isFinalState(state))
		{
			this.task.setFinished(true);
			System.out.println("Workflow beendet");
			return;
		}

		final List<WorkflowTransition> autotransitions = new ArrayList<>();
		for (final WorkflowTransition possibleTransition : this.getPossibleTransitions())
		{

			if (possibleTransition.getType() == TransistionType.SYSTEM)
			{
				final WorkflowRule rule = possibleTransition.getRule();
				if (this.executeRule(rule))
				{
					final String msg = "Regel: '" + rule.getName() + "' trifft zu";
					System.out.println(msg);

					autotransitions.add(possibleTransition);
					break;
				}
				else
				{
					final String msg = "Regel: '" + rule.getName() + "' trifft nicht zu";
					System.out.println(msg);
				}
			}
		}

		// einfache Lösung: erste automatische Transition mit erfüllter Regel wird ausgelöst!
		// TODO: falls mehrere automatische Tranistionen möglich sind --> echte Fehlerbehandlung
		Preconditions.checkState(autotransitions.size() <= 1);
		if (!autotransitions.isEmpty())
		{
			this.setState(autotransitions.getFirst().getToState());
		}

	}

	public void fireTransition(final WorkflowTransition transition)
	{
		Preconditions.checkState(this.getPossibleTransitions().contains(transition), "Transition nicht im Workflow");
		Preconditions.checkState(this.task.getCurrentStateObjId().get().equals(transition.getFromState().getObjId()), "Transition passt nicht zum aktuellen Zustand");

		// Aktuellen Zustand setzen
		this.setState(transition.getToState());

	}

	public boolean executeRule(final WorkflowRule rule)
	{
		final CompiledScript compiledScript = this.scriptBuilder.compileScript(rule.getScript());
		final Bindings b = new SimpleBindings();
		final String dataString = this.task.getData().getJson();
		b.put("data", dataString);
		try
		{
			return (boolean) compiledScript.eval(b);
		}
		catch (final ScriptException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public Optional<WorkflowState> getCurrentState()
	{
		return this.task.getCurrentStateObjId() //
				.map(value -> Optional.of(this.workflow.getStateByObjId(value))) //
				.orElseGet(Optional::empty);
	}

	public List<WorkflowTransition> getPossibleTransitions()
	{
		return this.task.getCurrentStateObjId() //
				.map(value -> this.workflow.getPossibleTransitions(value)) //
				.orElseGet(ArrayList::new);
	}

	public WorkflowTask getTask()
	{
		return this.task;
	}

}
