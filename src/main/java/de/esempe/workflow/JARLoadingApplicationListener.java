package de.esempe.workflow;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

public class JARLoadingApplicationListener implements ApplicationListener<ApplicationPreparedEvent>
{
	@Override
	public void onApplicationEvent(ApplicationPreparedEvent event)
	{
		// This is executed when the ApplicationContext is fully prepared but before Spring starts autowiring
		try
		{
			final ApplicationContext context = event.getApplicationContext();
			DynamicClassLoader.loadJar(context, "/home/etienne/projekte/java/pinghandler/build/libs/pingHandler.jar");
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}
}