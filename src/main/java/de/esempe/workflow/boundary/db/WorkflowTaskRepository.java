package de.esempe.workflow.boundary.db;

import org.bson.types.ObjectId;

import de.esempe.workflow.domain.WorkflowTask;

public interface WorkflowTaskRepository extends ExtendedMongoRepository<WorkflowTask, ObjectId>
{

}
