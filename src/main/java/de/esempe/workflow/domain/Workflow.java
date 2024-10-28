package de.esempe.workflow.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.google.common.base.MoreObjects;

@Document(collection = "workflows")
public class Workflow extends MongoDbObject
{
	private String name;

	@DocumentReference
	private Set<WorkflowTransition> transitions;

	private Map<ObjectId, List<WorkflowTransition>> fromStateTransitions;
	private Map<ObjectId, List<WorkflowTransition>> toStateTransitions;

//	@UsedByJsonAdapter(WorkflowJsonAdapter.class)
//	public static Workflow create(final ObjectId dbId, final UUID objId, final String name)
//	{
//		final var workflow = new Workflow(dbId, objId, name);
//		return workflow;
//	}
//
//	@UsedByJsonAdapter(WorkflowJsonAdapter.class)
//	public static Workflow create(final UUID objId, final String name)
//	{
//		final var workflow = new Workflow(null, objId, name);
//		return workflow;
//	}

	public static Workflow create(final String name)
	{
		final var state = new Workflow(name);
		return state;
	}

	Workflow()
	{
		super();
		this.name = "";
		this.transitions = new HashSet<>();
		this.fromStateTransitions = new HashMap<>();
		this.toStateTransitions = new HashMap<>();
	}

	private Workflow(final UUID objId, final String name)
	{
		super(objId);
		this.name = name;
		this.transitions = new HashSet<>();
		this.fromStateTransitions = new HashMap<>();
		this.toStateTransitions = new HashMap<>();
	}

	private Workflow(final String name)
	{
		this();
		this.name = name;
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

		if (!this.transitions.isEmpty())
		{
			// From-State muss bereit im Workflow enthalten sein!
			if (!this.fromStateTransitions.containsKey(fromState.getDbId()) && !this.toStateTransitions.containsKey(fromState.getDbId()))
			{
				throw new IllegalStateException("addTransition(): From-State noch nicht enthalten");
			}
		}

		List<WorkflowTransition> fromList = null;
		if (this.fromStateTransitions.containsKey(fromState.getDbId()))
		{
			fromList = this.fromStateTransitions.get(fromState.getDbId());
		}
		else
		{
			fromList = new ArrayList<>();
			this.fromStateTransitions.put(fromState.getDbId(), fromList);
		}
		fromList.add(transition);

		List<WorkflowTransition> toList = null;
		if (this.toStateTransitions.containsKey(toState.getDbId()))
		{
			toList = this.toStateTransitions.get(toState.getDbId());
		}
		else
		{
			toList = new ArrayList<>();
			this.toStateTransitions.put(toState.getDbId(), toList);
		}
		toList.add(transition);

		this.transitions.add(transition);

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
