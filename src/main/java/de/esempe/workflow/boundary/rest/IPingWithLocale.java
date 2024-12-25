package de.esempe.workflow.boundary.rest;

import java.util.Locale;

import org.springframework.context.ApplicationContext;

public interface IPingWithLocale
{
	String ping(Locale locale);

	default void setApplicationContext(ApplicationContext context)
	{
	}
}
