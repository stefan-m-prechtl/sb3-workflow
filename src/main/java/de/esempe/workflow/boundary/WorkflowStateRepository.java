package de.esempe.workflow.boundary;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import de.esempe.workflow.domain.WorkflowState;

public interface WorkflowStateRepository extends MongoRepository<WorkflowState, ObjectId>
{
	List<WorkflowState> findByName(String name);
}
