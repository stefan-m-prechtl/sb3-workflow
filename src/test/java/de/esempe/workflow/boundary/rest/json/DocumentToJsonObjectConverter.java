package de.esempe.workflow.boundary.rest.json;

import org.bson.Document;
import org.bson.json.JsonObject;
import org.springframework.core.convert.converter.Converter;

public class DocumentToJsonObjectConverter implements Converter<Document, JsonObject>
{
	@Override
	public JsonObject convert(final Document source)
	{
		return new JsonObject(source.toJson());
	}
}
