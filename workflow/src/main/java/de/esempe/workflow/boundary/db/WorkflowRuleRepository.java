package de.esempe.workflow.boundary.db;

import org.bson.types.ObjectId;

import de.esempe.workflow.domain.WorkflowRule;

public interface WorkflowRuleRepository extends ExtendedMongoRepository<WorkflowRule, ObjectId>
{

}
