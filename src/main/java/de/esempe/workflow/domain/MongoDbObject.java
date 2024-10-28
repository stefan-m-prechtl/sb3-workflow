package de.esempe.workflow.domain;

import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MongoDbObject
{
	@Id // id set from monogdb
	private ObjectId dbId;
	private UUID objId;

	MongoDbObject()
	{
		this.objId = UUID.randomUUID();
	}

	MongoDbObject(final UUID objId)
	{
		this.objId = objId;
	}

	public ObjectId getDbId()
	{
		return this.dbId;
	}

	public UUID getObjId()
	{
		return this.objId;
	}

}
