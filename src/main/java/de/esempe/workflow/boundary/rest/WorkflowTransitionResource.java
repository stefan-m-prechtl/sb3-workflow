package de.esempe.workflow.boundary.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.esempe.workflow.boundary.db.WorkflowTransitionRepository;
import de.esempe.workflow.domain.WorkflowTransition;

@RestController
@RequestMapping("transition")
public class WorkflowTransitionResource
{
	@Autowired
	WorkflowTransitionRepository repository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<WorkflowTransition>> getUsers()
	{
		final List<WorkflowTransition> all = this.repository.findAll();
		final var result = ResponseEntity.status(HttpStatus.OK).body(all);
		return result;
	}
}
