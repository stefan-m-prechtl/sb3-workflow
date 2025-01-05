package de.esempe.workflow.boundary.rest;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.esempe.workflow.boundary.db.ExtendedMongoRepository;
import de.esempe.workflow.domain.MongoDbObject;

class BaseResource<T extends MongoDbObject>
{
	private ExtendedMongoRepository<T, ObjectId> repository;

	BaseResource(final ExtendedMongoRepository<T, ObjectId> repository)
	{
		this.repository = repository;
	}

	ResponseEntity<List<T>> getAll()
	{
		final List<T> all = this.repository.findAll();
		final var result = ResponseEntity.status(HttpStatus.OK).body(all);
		return result;
	}

	public ResponseEntity<T> getOneByObjId(final UUID objId)
	{

		final T dbResult = this.repository.findByObjId(objId);
		if (null == dbResult)
		{
			final ResponseEntity<T> result = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			return result;
		}

		final var result = ResponseEntity.status(HttpStatus.OK).body(dbResult);
		return result;
	}

	public ResponseEntity<T> getOneByName(final String name)
	{

		final T dbResult = this.repository.findByName(name);
		if (null == dbResult)
		{
			final ResponseEntity<T> result = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			return result;
		}

		final var result = ResponseEntity.status(HttpStatus.OK).body(dbResult);
		return result;
	}

	public ResponseEntity<?> create(@RequestBody final T entity)
	{
		if (null != this.repository.findByName(entity.getName()))
		{
			final ResponseEntity<?> result = ResponseEntity//
					.status(HttpStatus.CONFLICT)//
					.body("Entität bereits enthalten");
			return result;
		}

		// Neues Objekt speichern
		final T saveedEntity = this.repository.save(entity);

		// URI für gespeichertes Objekt in header eintragen
		final String location = ServletUriComponentsBuilder //
				.fromCurrentRequest() //
				.path("/{objId}") //
				.buildAndExpand(saveedEntity.getObjId().toString()) //
				.toUriString();

		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(location));

		final ResponseEntity<?> result = ResponseEntity//
				.status(HttpStatus.CREATED)//
				.headers(headers) //
				.body(saveedEntity);

		return result;
	}

	public ResponseEntity<?> update(@PathVariable final UUID objId, @RequestBody final T enitity)
	{
		// Check objIds
		if (!enitity.getObjId().equals(objId))
		{
			final var result = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ObjIDs sind nicht identisch");
			return result;
		}

		final T existingEntity = this.repository.findByObjId(objId);
		// Check if entity exists
		if (null == existingEntity)
		{
			final var result = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			return result;
		}

		enitity.setIdFrom(existingEntity);
		final T savedEntity = this.repository.save(enitity);

		final var result = ResponseEntity.status(HttpStatus.OK).body(savedEntity);
		return result;
	}

	public ResponseEntity<?> delete(@PathVariable final UUID objId)
	{
		final T entity = this.repository.findByObjId(objId);
		if (null == entity)
		{
			final ResponseEntity<?> result = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			return result;
		}

		// Entität löschen
		this.repository.delete(entity);

		final var result = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return result;
	}

}
