package com.abui.soccer_system.repository;

import com.abui.soccer_system.model.Field;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource(path = "groups", collectionResourceRel = "groups")
@PreAuthorize("hasAuthority('ADMIN')")
public interface FieldRepository extends CrudRepository<Field, Long> {
    Field save(Field field);
}