package com.bnpp.cib.e2e.repositories;

import com.bnpp.cib.e2e.entities.LegalEntity;
import com.bnpp.cib.e2e.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LegalEntityJpaRepository extends JpaRepository<LegalEntity, UUID> {

}
