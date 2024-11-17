package de.esempe.workflow.boundary.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.esempe.workflow.boundary.db.WorkflowRepository;
import de.esempe.workflow.boundary.db.WorkflowTransitionRepository;
import de.esempe.workflow.domain.Workflow;
import de.esempe.workflow.domain.WorkflowTransition;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("workflow")
public class WorkflowResource
{
	@Autowired
	private WorkflowRepository repository;

	@Autowired
	WorkflowTransitionRepository repositoryTransition;

	// für Methoden Delegation
	private BaseResource<Workflow> resource;

	@PostConstruct
	void init()
	{
		this.resource = new BaseResource<>(this.repository);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Workflow>> getAll()
	{
		final var result = this.resource.getAll();
		return result;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody final Workflow workflow)
	{
		for (final WorkflowTransition transition : workflow.getTransitions())
		{
			final WorkflowTransition transistionFromDb = this.repositoryTransition.findByObjId(transition.getObjId());
			transition.setIdFrom(transistionFromDb);
		}

		final var result = this.resource.create(workflow);
		return result;
	}

}
