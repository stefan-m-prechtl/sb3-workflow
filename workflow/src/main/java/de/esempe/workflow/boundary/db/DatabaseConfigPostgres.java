package de.esempe.workflow.boundary.db;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "de.esempe.workflow.boundary.db")
public class DatabaseConfigPostgres
{

}
