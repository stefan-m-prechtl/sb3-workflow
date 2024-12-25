package de.esempe.workflow.boundary.db;

import org.bson.types.ObjectId;

import de.esempe.workflow.domain.Workflow;

public interface WorkflowRepository extends ExtendedMongoRepository<Workflow, ObjectId>
{
}
