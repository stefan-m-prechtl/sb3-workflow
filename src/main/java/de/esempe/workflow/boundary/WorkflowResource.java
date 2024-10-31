package de.esempe.workflow.boundary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.esempe.workflow.domain.Workflow;

@RestController
@RequestMapping("workflow")
public class WorkflowResource
{
	@Autowired
	private WorkflowRepository repository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Workflow>> getUsers()
	{
		final List<Workflow> all = this.repository.findAll();
		final var result = ResponseEntity.status(HttpStatus.OK).body(all);
		return result;
	}

}
