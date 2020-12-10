package com.bnpp.cib.e2e.services;

import com.bnpp.cib.e2e.entities.LegalEntity;
import com.bnpp.cib.e2e.entities.Request;
import com.bnpp.cib.e2e.repositories.RequestJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class RequestService {

    @Autowired
    private RequestJpaRepository requestJpaRepository;

    public List<Request> getRequests() {
        log.info("Get requests");
        return requestJpaRepository.findAll();
    }

    public Request getRequest(UUID id) {
        log.info("Get request uuid {}", id);

//        EntityManager entityManager;
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//
//        CriteriaQuery<LegalEntity> query = builder.createQuery(LegalEntity.class);
//        Root<LegalEntity> postComment = query.from(LegalEntity.class);
//
//        Join<LegalEntity, Request> post = postComment.join(LegalEntity_.request);
//
//        query.where(
//                builder.equal(
//                        post.get(Request_.name),
//                        "High-Performance Java Persistence"
//                )
//        );
//
//        List<LegalEntity> comments = entityManager
//                .createQuery(query)
//                .getResultList();


        return requestJpaRepository.findOne(id);
    }

    public Request createRequest(Request request) {
        log.info("Create Request {}", request.getId());

        for (LegalEntity legalEntity : request.getLegalEntities()) {
            legalEntity.setRequest(request);
        }
        Request createdRequest = requestJpaRepository.save(request);
        return createdRequest;
    }

    public Request updateRequest(UUID id, Request request) {

        for (LegalEntity legalEntity : request.getLegalEntities()) {
            legalEntity.setRequest(request);
        }
        Request updatedRequest = requestJpaRepository.save(request);

        return updatedRequest;
    }

    public void deleteRequest(UUID id) {
        requestJpaRepository.delete(id);
    }
}
