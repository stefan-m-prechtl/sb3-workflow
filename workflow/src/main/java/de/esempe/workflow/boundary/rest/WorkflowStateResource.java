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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.esempe.workflow.boundary.db.WorkflowStateRepository;
import de.esempe.workflow.domain.WorkflowState;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("state")
public class WorkflowStateResource
{
	@Autowired
	private WorkflowStateRepository repository;

	// für Methoden Delegation
	private BaseResource<WorkflowState> resource;

	@PostConstruct
	void init()
	{
		this.resource = new BaseResource<>(this.repository);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<WorkflowState>> getAll()
	{
		final var result = this.resource.getAll();
		return result;
	}

	@GetMapping(path = "/{objId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WorkflowState> getOneByObjId(@PathVariable final UUID objId)
	{
		final var result = this.resource.getOneByObjId(objId);
		return result;
	}

	@GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WorkflowState> searchByName(@RequestParam(required = true) final String name)
	{
		final var result = this.resource.getOneByName(name);
		return result;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody final WorkflowState state)
	{
		final var result = this.resource.create(state);
		return result;
	}

	@PutMapping(value = "/{objId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable final UUID objId, @RequestBody final WorkflowState state)
	{
		final var result = this.resource.update(objId, state);
		return result;
	}

	@DeleteMapping(path = "/{objId}")
	public ResponseEntity<?> delete(@PathVariable final UUID objId)
	{
		final var result = this.resource.delete(objId);
		return result;
	}

}
