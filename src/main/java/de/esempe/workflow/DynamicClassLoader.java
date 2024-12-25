package de.esempe.workflow;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class DynamicClassLoader
{
	public static void loadJar(ApplicationContext context, String jarPath) throws Exception
	{
		final File jarFile = new File(jarPath);
		final URL[] urls = { jarFile.toURI().toURL() };
		final URLClassLoader urlClassLoader = new URLClassLoader(urls, DynamicClassLoader.class.getClassLoader());

		// Output loaded URLs to confirm JAR loading
//		System.out.println("Loaded JARs: ");
//		for (final URL url : urlClassLoader.getURLs())
//		{
//			System.out.println(url);
//		}
//
//		final Class<?> clazz = Class.forName("de.esempe.workflow.plugin.PingWithLocale", false, urlClassLoader);
//		System.out.println("Loaded class: " + clazz.getName());

		final GenericApplicationContext appContext = (GenericApplicationContext) context;
		appContext.setClassLoader(urlClassLoader);
		final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(appContext);
		reader.loadBeanDefinitions("classpath:/plugins.xml");

	}
}
