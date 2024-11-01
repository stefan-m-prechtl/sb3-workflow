package de.esempe.workflow.boundary.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.databind.JsonDeserializer;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UsedByJsonDeserializer
{
	Class<? extends JsonDeserializer<?>> value();
}
