package com.bnpp.cib.e2e.repositories;

import com.bnpp.cib.e2e.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RequestJpaRepository extends JpaRepository<Request, UUID> {

}
