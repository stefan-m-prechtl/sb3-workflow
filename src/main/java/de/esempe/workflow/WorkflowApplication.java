package de.esempe.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication

public class WorkflowApplication
{
	public static void main(final String[] args)
	{
		SpringApplication.run(WorkflowApplication.class, args);
	}
}
