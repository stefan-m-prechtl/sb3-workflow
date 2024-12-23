package de.esempe.workflow.boundary.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.esempe.workflow.domain.GlobalRole;

public interface GlobalRoleRespository extends JpaRepository<GlobalRole, Integer>
{
	List<GlobalRole> findByRoleName(String rolename);

}
