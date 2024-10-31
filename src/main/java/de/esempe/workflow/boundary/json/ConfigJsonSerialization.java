package de.esempe.workflow.boundary.json;

import org.springframework.stereotype.Component;

@Component
public class ConfigJsonSerialization
{
	private boolean fullObjReference = true;

	public boolean withFullObjReference()
	{
		return this.fullObjReference;
	}

	public void setFullObjReference(final boolean fullObjReference)
	{
		this.fullObjReference = fullObjReference;
	}
}
