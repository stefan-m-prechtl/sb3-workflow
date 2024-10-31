package de.esempe.workflow.boundary;

import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import de.esempe.workflow.domain.Workflow;

public interface WorkflowRepository extends MongoRepository<Workflow, ObjectId>
{
	// Name ist eindeutig
	Workflow findByName(String name);

	// ObjId ist eindeutig
	Workflow findByObjId(UUID objId);

}
