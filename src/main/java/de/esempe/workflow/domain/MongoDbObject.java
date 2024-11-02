package de.esempe.workflow.domain;

import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MongoDbObject
{
	@Id // database-id: set from monogdb
	private ObjectId dbId;
	protected UUID objId;

	@Indexed(unique = true)
	protected String name;

	MongoDbObject()
	{
		this.objId = UUID.randomUUID();
		this.name = "";
	}

	MongoDbObject(final UUID objId)
	{
		this.objId = objId;
		this.name = "";
	}

	public ObjectId getDbId()
	{
		return this.dbId;
	}

	public UUID getObjId()
	{
		return this.objId;
	}

	public void setIdFrom(final MongoDbObject obj)
	{
		this.dbId = obj.dbId;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

}
