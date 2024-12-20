package com.abui.soccer_system.repository;

import com.abui.soccer_system.model.SoccerField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource(path = "soccer_fields")
public interface SoccerFieldRepository extends JpaRepository<SoccerField, Long> {

    @PreAuthorize("hasAuthority('ADMIN')")
    <S extends SoccerField> S save(S entity);

    @PreAuthorize("hasAuthority('ADMIN')")
    void deleteById(Long aLong);

    @PreAuthorize("hasAuthority('ADMIN')")
    void delete(SoccerField entity);

    @PreAuthorize("hasAuthority('ADMIN')")
    void deleteAll();
}