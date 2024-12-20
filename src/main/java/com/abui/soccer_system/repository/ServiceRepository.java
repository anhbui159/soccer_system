package com.abui.soccer_system.repository;

import com.abui.soccer_system.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "services", collectionResourceRel = "services")
public interface ServiceRepository extends JpaRepository<Service, Long> {
}