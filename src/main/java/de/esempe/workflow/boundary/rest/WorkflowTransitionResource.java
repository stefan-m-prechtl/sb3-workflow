package de.esempe.workflow.boundary.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.esempe.workflow.boundary.db.WorkflowTransitionRepository;
import de.esempe.workflow.domain.WorkflowTransition;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("transition")
public class WorkflowTransitionResource
{
	@Autowired
	WorkflowTransitionRepository repository;

	// für Methoden Delegation
	private BaseResource<WorkflowTransition> resource;

	@PostConstruct
	void init()
	{
		this.resource = new BaseResource<>(this.repository);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<WorkflowTransition>> getAll()
	{
		final var result = this.resource.getAll();
		return result;
	}

	@GetMapping(path = "/{objId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WorkflowTransition> getOneByObjId(@PathVariable final UUID objId)
	{
		final var result = this.resource.getOneByObjId(objId);
		return result;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody final WorkflowTransition transition)
	{
		final var result = this.resource.create(transition);
		return result;
	}

	@PutMapping(value = "/{objId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable final UUID objId, @RequestBody final WorkflowTransition transition)
	{
		final var result = this.resource.update(objId, transition);
		return result;
	}

	@DeleteMapping(path = "/{objId}")
	public ResponseEntity<?> delete(@PathVariable final UUID objId)
	{
		final var result = this.resource.delete(objId);
		return result;
	}

}