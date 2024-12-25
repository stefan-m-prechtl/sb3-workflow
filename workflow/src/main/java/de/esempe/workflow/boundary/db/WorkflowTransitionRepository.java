package de.esempe.workflow.boundary.db;

import org.bson.types.ObjectId;

import de.esempe.workflow.boundary.db.listener.HasRepositoryListener;
import de.esempe.workflow.boundary.db.listener.WorkflowTaskRepositoryListener;
import de.esempe.workflow.domain.WorkflowTransition;

@HasRepositoryListener(WorkflowTaskRepositoryListener.class)
public interface WorkflowTransitionRepository extends ExtendedMongoRepository<WorkflowTransition, ObjectId>
{

}
