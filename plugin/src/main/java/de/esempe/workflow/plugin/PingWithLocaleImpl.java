package de.esempe.workflow.plugin;

import java.util.Locale;

import de.esempe.workflow.boundary.rest.PingWithLocale;

public class PingWithLocaleImpl implements PingWithLocale
{
	public PingWithLocaleImpl()
	{

	}

	@Override
	public String ping(Locale locale)
	{
		if (locale == Locale.GERMAN)
		{
			return "Ping vom Backend";
		}
		return "ping from backend";
	}
}
