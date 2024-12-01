package de.esempe.workflow.boundary.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import de.esempe.workflow.boundary.db.WorkflowRepository;
import de.esempe.workflow.boundary.db.WorkflowTaskRepository;
import de.esempe.workflow.boundary.db.WorkflowTransitionRepository;
import de.esempe.workflow.controller.TaskController;
import de.esempe.workflow.domain.Workflow;
import de.esempe.workflow.domain.WorkflowTask;
import de.esempe.workflow.domain.WorkflowTransition;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("task")
public class WorkflowTaskResource
{
	@Autowired
	private WorkflowTaskRepository repository;
	@Autowired
	private WorkflowRepository repositoryWorkflow;
	@Autowired
	private WorkflowTransitionRepository repositoryTransition;

	// für Methoden Delegation
	private BaseResource<WorkflowTask> resource;

	@PostConstruct
	void init()
	{
		this.resource = new BaseResource<>(this.repository);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<WorkflowTask>> getAll()
	{
		final var result = this.resource.getAll();
		return result;
	}

	@GetMapping(path = "/{objId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WorkflowTask> getOneByObjId(@PathVariable final UUID objId)
	{
		final var result = this.resource.getOneByObjId(objId);
		return result;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody final WorkflowTask task)
	{
		final var result = this.resource.create(task);
		return result;
	}

	@PutMapping(value = "/{objId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable final UUID objId, @RequestBody final WorkflowTask task)
	{
		final var result = this.resource.update(objId, task);
		return result;
	}

	@DeleteMapping(path = "/{objId}")
	public ResponseEntity<?> delete(@PathVariable final UUID objId)
	{
		final var result = this.resource.delete(objId);
		return result;
	}

	// *************************************************************************************************************
	// Workflow-Logik

	@PutMapping(value = "/{objId}/start", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> start(@PathVariable final UUID objId, @RequestBody final WorkflowTask task)
	{
		final Workflow workflow = this.repositoryWorkflow.findByObjId(task.getWorkflowObjId());
		final TaskController controller = TaskController.create(workflow, task);

		controller.startTask();

		final WorkflowTask updatedTask = controller.getTask();
		final var result = this.resource.update(objId, updatedTask);
		return result;
	}

	@GetMapping(path = "/{objId}/transition", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<WorkflowTransition>> getPossibleTransitions(@PathVariable final UUID objId)
	{
		final WorkflowTask task = this.repository.findByObjId(objId);
		final Workflow workflow = this.repositoryWorkflow.findByObjId(task.getWorkflowObjId());

		final TaskController controller = TaskController.create(workflow, task);
		final List<WorkflowTransition> transistions = controller.getPossibleTransitions();
		final var result = ResponseEntity.status(HttpStatus.OK).body(transistions);
		return result;
	}

	@PutMapping(value = "/{objIdTask}/transition/{objIdTransition}/fire", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fire(@PathVariable final UUID objIdTask, @PathVariable final UUID objIdTransition)
	{
		final WorkflowTask task = this.repository.findByObjId(objIdTask);
		final Workflow workflow = this.repositoryWorkflow.findByObjId(task.getWorkflowObjId());
		final WorkflowTransition transition = this.repositoryTransition.findByObjId(objIdTransition);

		final TaskController controller = TaskController.create(workflow, task);
		controller.fireTransition(transition);

		final WorkflowTask updatedTask = controller.getTask();
		final var result = this.resource.update(objIdTask, updatedTask);
		return result;
	}

}
