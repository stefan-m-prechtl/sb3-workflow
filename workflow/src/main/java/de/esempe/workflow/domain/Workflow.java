package de.esempe.workflow.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

@Document(collection = "workflows")
public class Workflow extends MongoDbObject
{
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

	public static Workflow create(final UUID objId, final String name)
	{
		final var result = new Workflow();
		result.objId = objId;
		result.name = name;
		return result;
	}

	public static Workflow create(final String name)
	{
		final var result = new Workflow();
		result.name = name;
		return result;
	}

	public Set<WorkflowTransition> getTransitions()
	{
		return this.transitions;
	}

	public void addTransition(final WorkflowTransition transition)
	{
		if (this.transitions.contains(transition))
		{
			return;
		}

		final var fromState = transition.getFromState();
		final var toState = transition.getToState();

		if (this.transitions.isEmpty())
		{
			this.transitions.add(transition);
			this.fromStates.add(fromState);
			this.toStates.add(toState);
		}
		else // toState muss in der Liste der fromStates sein!
		{
			Preconditions.checkState(this.getStates().contains(transition.getFromState()), "Startzustand der Transition nicht im Workflow enthalten!");
			this.transitions.add(transition);
			this.fromStates.add(fromState);
			this.toStates.add(toState);
		}
	}

	public WorkflowState getStartState()
	{
		final var copyFromStates = new HashSet<>(this.fromStates);
		copyFromStates.removeAll(this.toStates);
		// Preconditions.checkState(copyFromStates.size() == 1, "Mehr als ein Startzustand");

		final WorkflowState result = copyFromStates.stream().findFirst().get();
		return result;
	}

	public List<WorkflowState> getFinalStates()
	{
		final var copyToStates = new HashSet<>(this.toStates);
		copyToStates.removeAll(this.fromStates);

		final List<WorkflowState> result = copyToStates.stream().toList();
		return result;
	}

	public boolean isFinalState(final WorkflowState fromState)
	{
		final var result = this.getFinalStates().contains(fromState);
		return result;
	}

	public Set<WorkflowState> getStates()
	{
		final var result = new HashSet<WorkflowState>();
		result.addAll(this.fromStates);
		result.addAll(this.toStates);
		return result;
	}

	public WorkflowState getStateByObjId(final UUID currentStateObjId)
	{
		final Set<WorkflowState> allStates = this.getStates();
		final WorkflowState result = allStates.stream() //
				.filter(s -> s.getObjId().equals(currentStateObjId)) //
				.findFirst() //
				.get();

		return result;
	}

	public WorkflowState getStateByName(final String name)
	{
		final Set<WorkflowState> allStates = this.getStates();
		final WorkflowState result = allStates.stream() //
				.filter(s -> s.getName().equals(name)) //
				.findFirst() //
				.get();

		return result;
	}

	public List<WorkflowTransition> getPossibleTransitions(final UUID objId)
	{
		final List<WorkflowTransition> result = this.transitions.stream().filter(t -> t.getFromState().getObjId().equals(objId)).toList();
		return result;
	}

	public List<WorkflowTransition> getPossibleTransitions(final String name)
	{
		final List<WorkflowTransition> result = this.transitions.stream().filter(t -> t.getFromState().getName().equals(name)).toList();
		return result;
	}

	@Override
	public String toString()
	{
		final var result = MoreObjects.toStringHelper(this) //
				.add("objid", this.getObjId().toString())//
				.add("name", this.name) //
				.toString();
		return result;
	}

}
