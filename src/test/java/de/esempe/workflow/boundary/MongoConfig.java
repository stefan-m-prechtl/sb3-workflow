package de.esempe.workflow.boundary;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import de.esempe.workflow.boundary.json.DocumentToJsonObjectConverter;

@Configuration
public class MongoConfig
{
	@Bean
	MongoCustomConversions customConversions()
	{
		return new MongoCustomConversions(Collections.singletonList(new DocumentToJsonObjectConverter()));
	}
}
