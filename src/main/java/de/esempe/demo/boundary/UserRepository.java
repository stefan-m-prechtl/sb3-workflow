package de.esempe.demo.boundary;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.esempe.demo.domain.User;

public interface UserRepository extends CrudRepository<User, Integer>
{
	@Query(value = """
			SELECT * FROM restdemo.t_user
			""", nativeQuery = true)
	User findStefan();

}
