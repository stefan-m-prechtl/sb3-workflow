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
	private static String FIELD_ID = "id";
	private static String FIELD_FIRSTNAME = "first";
	private static String FIELD_LASTNAME = "last";

	public static class UserJsonSerializer extends JsonSerializer<User>
	{
		@Override
		public void serialize(final User user, final JsonGenerator generator, final SerializerProvider serializers) throws IOException
		{
			generator.writeStartObject();

			// Get java values and write it into json string
			generator.writeNumberField(FIELD_ID, user.getId());
			generator.writeStringField(FIELD_FIRSTNAME, user.getFirstname());
			generator.writeStringField(FIELD_LASTNAME, user.getLastname());

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
			// Parse json string into a node
			final JsonNode node = parser.getCodec().readTree(parser);
			// ID vorhanden?
			if (null != node.get(FIELD_ID))
			{
				final int id = node.get(FIELD_ID).asInt();
				user.setId(id);
			}

			// Musswerte
			final String firstname = node.get(FIELD_FIRSTNAME).asText();
			final String lastname = node.get(FIELD_LASTNAME).asText();
			// Set java values
			user.setFirstname(firstname);
			user.setLastname(lastname);

			return user;
		}
	}
}
