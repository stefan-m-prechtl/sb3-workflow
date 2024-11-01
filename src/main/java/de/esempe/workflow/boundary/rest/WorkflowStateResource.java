package de.esempe.workflow.boundary.rest;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.esempe.workflow.boundary.db.WorkflowStateRepository;
import de.esempe.workflow.domain.WorkflowState;

@RestController
@RequestMapping("state")
public class WorkflowStateResource
{
	@Autowired
	private WorkflowStateRepository repository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<WorkflowState>> getAll()
	{
		final List<WorkflowState> all = this.repository.findAll();
		final var result = ResponseEntity.status(HttpStatus.OK).body(all);
		return result;
	}

	@GetMapping(path = "/{objId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WorkflowState> getOneByObjId(@PathVariable final UUID objId)
	{

		final WorkflowState dbResult = this.repository.findByObjId(objId);
		if (null == dbResult)
		{
			final ResponseEntity<WorkflowState> result = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			return result;
		}

		final var result = ResponseEntity.status(HttpStatus.OK).body(dbResult);
		return result;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody final WorkflowState state)
	{
		if (null != this.repository.findByName(state.getName()))
		{
			final ResponseEntity<?> result = ResponseEntity//
					.status(HttpStatus.CONFLICT)//
					.body("Workflow bereits enthalten");
			return result;
		}

		// Neues Objekt speichern
		final WorkflowState savedState = this.repository.save(state);

		// URI für gespeichertes Objekt in header eintragen
		final String location = ServletUriComponentsBuilder //
				.fromCurrentRequest() //
				.path("/{objId}") //
				.buildAndExpand(savedState.getObjId().toString()) //
				.toUriString();

		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(location));

		final ResponseEntity<?> result = ResponseEntity//
				.status(HttpStatus.CREATED)//
				.headers(headers) //
				.body(savedState);

		return result;

	}

}
