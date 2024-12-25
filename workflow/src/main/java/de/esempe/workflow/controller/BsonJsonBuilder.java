package de.esempe.workflow.controller;

import java.util.HashMap;
import java.util.Map;

import org.bson.json.JsonObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BsonJsonBuilder
{
	private final Map<String, Object> mapKeyValue;

	private BsonJsonBuilder()
	{
		this.mapKeyValue = new HashMap<>();
	}

	public static BsonJsonBuilder create()
	{
		final var result = new BsonJsonBuilder();
		return result;
	}

	public BsonJsonBuilder add(final String key, final Object value)
	{
		this.mapKeyValue.put(key, value);
		return this;
	}

	public JsonObject build()
	{
		try
		{
			final ObjectMapper objectMapper = new ObjectMapper();
			final String jsonString = objectMapper.writeValueAsString(this.mapKeyValue);
			final var result = new JsonObject(jsonString);
			return result;
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}

	}

}
