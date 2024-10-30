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
		// Statuswechsel: Leave-Skript vom Status der verlassen wird ausführen, Enter-Skript vom Status der gesetzt wird aufführen
		this.executeScriptLeavePreviousState();
		this.task.setCurrentStateObjId(state.getObjId());
		this.executeScriptEnterCurrentState();

		// Neu Status ist finaler Status? Workflow endet!
		if (this.workflow.isFinalState(state))
		{
			this.task.setFinished(true);
			System.out.println("Workflow beendet");
			return;
		}

		// Durchlaufe alle möglichen Transitionen:
		// die erste automatische Tranisition, deren Regel erfüllt wird, wird ausgelöst!
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

	private void executeScriptEnterCurrentState()
	{
		final var currentState = this.getCurrentState().get();
		this.executeScript(currentState.getScriptLeave());
	}

	private void executeScriptLeavePreviousState()
	{
		if (this.getCurrentState().isPresent())
		{
			final var previoiusState = this.getCurrentState().get();
			this.executeScript(previoiusState.getScriptLeave());
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

	private void executeScript(final String groovyScript)
	{
		if (groovyScript.isEmpty())
		{
			return;
		}

		final CompiledScript compileScriptLeave = this.scriptBuilder.compileScript(groovyScript);
		try
		{
			compileScriptLeave.eval();
		}
		catch (final ScriptException e)
		{
			throw new RuntimeException(e);
		}
	}

}
