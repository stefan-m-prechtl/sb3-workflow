package de.esempe.workflow.boundary;

import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import de.esempe.workflow.domain.WorkflowState;

public interface WorkflowStateRepository extends MongoRepository<WorkflowState, ObjectId>
{
	// Name ist eindeutig
	WorkflowState findByName(String name);

	// ObjId ist eindeutig
	WorkflowState findByObjId(UUID objId);
}
