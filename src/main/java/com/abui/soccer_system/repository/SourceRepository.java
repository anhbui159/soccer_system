package com.abui.soccer_system.repository;

import com.abui.soccer_system.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
public interface SourceRepository extends JpaRepository<Source, Long> {

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    <S extends Source> S save(S entity);

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    void deleteById(Long aLong);
}