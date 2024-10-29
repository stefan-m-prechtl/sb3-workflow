package de.esempe.workflow.boundary;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import de.esempe.workflow.domain.Workflow;

public interface WorkflowRepository extends MongoRepository<Workflow, ObjectId>
{

}
