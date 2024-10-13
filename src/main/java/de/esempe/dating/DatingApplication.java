package de.esempe.dating;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DatingApplication
{
	public static void main(final String[] args)
	{
		final SpringApplicationBuilder bld = new SpringApplicationBuilder(DatingApplication.class) //
				.headless(false)//
				.bannerMode(Mode.OFF) //
				.logStartupInfo(true);

		final ApplicationContext applicationContext = bld.run(args);

		// Arrays.stream(applicationContext.getBeanDefinitionNames()).sorted().forEach(System.out::println);
		// final FileSystem fs = applicationContext.getBean(FileSystem.class);
		// final long gigabytes = DataSize.ofBytes(fs.getFreeDiskSpace()).toGigabytes();

		// System.out.println("Freier Speicherplatz (GB): " + gigabytes);
	}
}
