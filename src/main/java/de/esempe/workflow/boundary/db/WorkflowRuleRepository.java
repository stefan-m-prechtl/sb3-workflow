package de.esempe.workflow.boundary.db;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import de.esempe.workflow.domain.WorkflowRule;

public interface WorkflowRuleRepository extends MongoRepository<WorkflowRule, ObjectId>
{

}
