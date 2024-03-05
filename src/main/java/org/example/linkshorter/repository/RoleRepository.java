package org.example.linkshorter.repository;

import org.example.linkshorter.entity.Role;
import org.example.linkshorter.entity.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(UserRole userRole);
}
