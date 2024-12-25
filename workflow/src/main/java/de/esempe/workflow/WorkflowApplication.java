package de.esempe.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkflowApplication
{

	public static void main(final String[] args)
	{
		// SpringApplication.run(WorkflowApplication.class, args);

		final SpringApplication app = new SpringApplication(WorkflowApplication.class);
		app.addListeners(new JARLoadingApplicationListener());
		app.run(args);

	}
}
