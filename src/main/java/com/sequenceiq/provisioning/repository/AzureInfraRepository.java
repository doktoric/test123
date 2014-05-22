package com.sequenceiq.provisioning.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostAuthorize;

import com.sequenceiq.provisioning.domain.AzureInfra;
import com.sequenceiq.provisioning.domain.User;

public interface AzureInfraRepository extends CrudRepository<AzureInfra, Long> {

    User findByName(String name);

    @PostAuthorize("returnObject?.user?.id == principal?.id")
    AzureInfra findOne(Long id);
}