package de.esempe.workflow.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

@Document(collection = "workflows")
public class Workflow extends MongoDbObject
{
	private String name;

	@DocumentReference
	private Set<WorkflowTransition> transitions;

	@Transient
	private Set<WorkflowState> fromStates;
	@Transient
	private Set<WorkflowState> toStates;

	private Workflow()
	{
		super();
		this.name = "";
		this.transitions = new HashSet<>();
		this.fromStates = new HashSet<>();
		this.toStates = new HashSet<>();
	}

	public static Workflow create(final String name)
	{
		final var result = new Workflow();
		result.name = name;
		return result;
	}

	public String getName()
	{
		return this.name;
	}

	public Set<WorkflowTransition> getTransitions()
	{
		return this.transitions;
	}

	public void addTransition(final WorkflowTransition transition)
	{
		final var fromState = transition.getFromState();
		final var toState = transition.getToState();

		if (this.transitions.isEmpty())
		{
			this.transitions.add(transition);
			this.fromStates.add(transition.getFromState());
			this.toStates.add(transition.getToState());
		}
		else // toState muss in der Liste der fromStates sein!
		{
			Preconditions.checkState(this.fromStates.contains(transition.getFromState()), "Startzustand der Transition nicht im Workflow enthaltenS");
			this.transitions.add(transition);
			this.fromStates.add(transition.getFromState());
			this.toStates.add(transition.getToState());
		}
	}

	public WorkflowState getStartState()
	{
		final var copyFromStates = new HashSet<>(this.fromStates);
		copyFromStates.removeAll(this.toStates);
		Preconditions.checkState(copyFromStates.size() == 1, "Mehr als ein Startzustand");

		final WorkflowState result = copyFromStates.stream().findFirst().get();
		return result;
	}

	@Override
	public String toString()
	{
		final var result = MoreObjects.toStringHelper(this) //
				.add("id", this.getDbId().toString())//
				.add("name", this.name) //
				.toString();
		return result;
	}
}
