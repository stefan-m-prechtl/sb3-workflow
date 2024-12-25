package de.esempe.workflow.boundary.db;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ExtendedMongoRepository<T, ID> extends MongoRepository<T, ID>
{
	// Name ist eindeutig
	T findByName(String name);

	// ObjId ist eindeutig
	T findByObjId(UUID objId);

}
