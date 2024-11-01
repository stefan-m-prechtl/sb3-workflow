package de.esempe.workflow.boundary.db;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import de.esempe.workflow.domain.WorkflowTransition;

@HasRepositoryListener(WorkflowTaskRepositoryListener.class)
public interface WorkflowTransitionRepository extends MongoRepository<WorkflowTransition, ObjectId>
{

}
