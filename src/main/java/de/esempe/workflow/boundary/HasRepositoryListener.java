package de.esempe.workflow.boundary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HasRepositoryListener
{
	Class<? extends AbstractMongoEventListener<?>> value();
}
