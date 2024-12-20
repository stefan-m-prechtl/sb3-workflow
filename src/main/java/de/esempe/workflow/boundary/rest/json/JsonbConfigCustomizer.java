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
						new UserJsonAdapter()//
				);

		return JsonbBuilder.create(config);
	}
}
