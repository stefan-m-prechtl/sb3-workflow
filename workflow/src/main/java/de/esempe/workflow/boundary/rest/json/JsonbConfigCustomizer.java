package de.esempe.workflow.boundary.rest.json;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

@Configuration
public class JsonbConfigCustomizer
{
	@Bean
	Jsonb jsonb()
	{
		final JsonbConfig config = new JsonbConfig()//
				.withAdapters(//
						new WorkflowStateJsonAdapter(), //
						new WorkflowTransitionJsonAdapter(), //
						new WorkflowTaskJsonAdapter(), //
						new WorkflowJsonAdapter(), //
						new UserJsonAdapter()//
				);

		return JsonbBuilder.create(config);
	}
}
