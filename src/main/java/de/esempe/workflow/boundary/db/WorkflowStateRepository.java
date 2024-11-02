package de.esempe.workflow.boundary.db;

import org.bson.types.ObjectId;

import de.esempe.workflow.domain.WorkflowState;

public interface WorkflowStateRepository extends ExtendedMongoRepository<WorkflowState, ObjectId>
{
}
