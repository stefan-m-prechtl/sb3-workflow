package de.esempe.workflow.boundary.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.esempe.workflow.boundary.db.WorkflowRepository;
import de.esempe.workflow.boundary.rest.json.ConfigJsonSerialization;
import de.esempe.workflow.domain.Workflow;

@RestController
@RequestMapping("workflow")
public class WorkflowResource
{
	@Autowired
	private WorkflowRepository repository;

// Demo: hier soll später per plugin ein Exporter nachgeladen werden
//	@Autowired(required = false)
//	private Exporter exporter;
//
//	@PostConstruct
//	public void init()
//	{
//		if (null == this.exporter)
//		{
//			System.out.println("Exporter nicht geladen");
//		}
//	}

	@Autowired
	ConfigJsonSerialization serializationConfig;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Workflow>> getAll()
	{
		this.serializationConfig.setFullObjReference(false);

		final List<Workflow> all = this.repository.findAll();
		final var result = ResponseEntity.status(HttpStatus.OK).body(all);
		return result;
	}

	@GetMapping(path = "/{objId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getOneByObjId(@PathVariable final UUID objId)
	{
		this.serializationConfig.setFullObjReference(true);

		final Workflow dbResult = this.repository.findByObjId(objId);
		if (null == dbResult)
		{
			final ResponseEntity<?> result = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			return result;
		}

		final var result = ResponseEntity.status(HttpStatus.OK).body(dbResult);
		return result;
	}

}
