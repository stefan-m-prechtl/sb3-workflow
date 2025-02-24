package de.esempe.workflow.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional
@Rollback(false)
abstract class AbstractEntityTest<E extends AbstractEntity>
{
	@PersistenceContext
	protected EntityManager em;

	// konkrete Entitätsklasse - wird per Konstruktor gesetzt
	protected Class<E> entityClass;
	protected E objUnderTest;
	protected long id;

	private List<String> initialQueries;

	// BeforeAll
	protected void setUp(final List<String> initialQueries, final Class<E> entityClass)
	{
		this.objUnderTest = this.createObjUnderTest();
		this.id = this.objUnderTest.getId();

		this.entityClass = entityClass;
		this.initialQueries = initialQueries;

	}

	@Test
	@DisplayName("Delete test data")
	@Order(0)
	void resetData()
	{
		for (final String qry : this.initialQueries)
		{
			this.em.createNativeQuery(qry).executeUpdate();
		}
	}

	@Test
	@DisplayName("Create entity")
	@Order(1)
	void create()
	{
		// act
		this.em.persist(this.objUnderTest);
		// assert
		assertThat(this.objUnderTest.getId()).isGreaterThan(0L);
		this.id = this.objUnderTest.getId();
	}

	@Test
	@DisplayName("Read entity")
	@Order(2)
	void read()
	{
		// act
		final var readEntity = this.em.find(this.entityClass, this.id);
		// assert
		assertAll( //
				() -> assertThat(readEntity).isNotNull(), //
				() -> assertThat(readEntity.getId()).isEqualTo(this.id));
	}

	@Test
	@DisplayName("Update entity")
	@Order(3)
	void update()
	{
		// act
		final var updatedEntity = this.em.merge(this.updateObjUnderTest(this.objUnderTest));

		// assert
		assertAll(//
				() -> assertThat(updatedEntity).isNotNull(), //
				() -> assertThat(updatedEntity.getId()).isEqualTo(this.objUnderTest.getId()));
	}

	@Test
	@DisplayName("Delete entity")
	@Order(4)
	void delete()
	{
		// act
		final var entity = this.em.merge(this.objUnderTest);
		this.em.remove(entity);
		// assert
		final var readEntity = this.em.find(this.entityClass, this.id);
		assertThat(readEntity).isNull();

	}

	protected abstract E createObjUnderTest();

	protected abstract E updateObjUnderTest(E entity);

}
