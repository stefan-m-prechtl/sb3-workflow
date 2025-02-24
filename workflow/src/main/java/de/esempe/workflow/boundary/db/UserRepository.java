package de.esempe.workflow.boundary.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.esempe.workflow.domain.User;

interface UserRepository extends JpaRepository<User, Long>, AdditionalUserRepository
{
	// "automatische" Query "byName" durch Spring
	List<User> findByLastname(String lastname);

	List<User> findByUsername(String username);

}
