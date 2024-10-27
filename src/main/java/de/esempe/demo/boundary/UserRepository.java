package de.esempe.demo.boundary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.esempe.demo.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>, AdditionalUserRepository
{
	// Demo für manuelle SQL-Query
	@Query(value = """
			SELECT * FROM restdemo.t_user
			""", nativeQuery = true)
	User findStefan();

	// Demo für "automatische" Query "byName" durch Spring
	// find a user by their lastname
	List<User> findByLastname(String lastname);

}
