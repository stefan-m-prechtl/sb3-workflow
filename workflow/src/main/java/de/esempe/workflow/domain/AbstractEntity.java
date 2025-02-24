package de.esempe.workflow.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	AbstractEntity()
	{
		this.id = -1L;
	}

	public long getId()
	{
		return this.id;
	}

}
