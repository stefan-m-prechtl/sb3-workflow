package de.esempe.demo.boundary;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import de.esempe.demo.domain.User;

@JsonComponent
public class UserJsonAdapter
{
	public static class UserJsonSerializer extends JsonSerializer<User>
	{
		@Override
		public void serialize(final User user, final JsonGenerator generator, final SerializerProvider serializers) throws IOException
		{
			generator.writeStartObject();

			generator.writeStringField("first", user.getFirstname());
			generator.writeStringField("last", user.getLastname());

			generator.writeEndObject();
		}
	}

	public static class UserJsonDeserializer extends JsonDeserializer<User>
	{
		@Override
		public User deserialize(final JsonParser parser, final DeserializationContext ctxt) throws IOException, JacksonException
		{
			// Create a User object
			final User user = new User();

			final JsonNode node = parser.getCodec().readTree(parser);
			final String firstname = node.get("first").asText();
			final String lastname = node.get("lastt").asText();

			user.setFirstname(firstname);
			user.setLastname(lastname);

			return user;
		}

	}
}
