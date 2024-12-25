package de.esempe.workflow;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class DynamicClassLoader
{
	public static void loadJar(ApplicationContext context) throws Exception
	{
		final Environment environment = context.getEnvironment();

		final boolean enabled = environment.getProperty("plugin.enabled", Boolean.class, false);

		if (enabled)
		{
			final String jarPath = environment.getProperty("plugin.path", "");
			if (!jarPath.isBlank())
			{
				final File jarFile = new File(jarPath);
				if (!jarFile.exists() || !jarFile.isFile())
				{
					throw new IllegalArgumentException("Invalid JAR path: " + jarPath);
				}

				final URL[] urls;
				try
				{
					urls = new URL[] { jarFile.toURI().toURL() };
				}
				catch (final MalformedURLException e)
				{
					throw new RuntimeException("Failed to convert JAR path to URL: " + jarPath, e);
				}

				final URLClassLoader urlClassLoader = new URLClassLoader(urls, DynamicClassLoader.class.getClassLoader());

				final GenericApplicationContext appContext = (GenericApplicationContext) context;
				appContext.setClassLoader(urlClassLoader);

				final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(appContext);
				final Resource resource = new PathMatchingResourcePatternResolver(urlClassLoader).getResource("classpath:/plugins.xml");
				reader.loadBeanDefinitions(resource);
			}
		}
	}
}
