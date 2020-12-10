package com.bnpp.cib.e2e.repositories;

import com.bnpp.cib.e2e.entities.LegalEntity;
import com.bnpp.cib.e2e.entities.Request;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
//@TestMethodOrder
public class RequestRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RequestJpaRepository requestJpaRepository;

    @Test
    public void shouldCreateRequest() {
        // GIVEN
        Request request1 = new Request();
        request1.setName("Request 1");
        LegalEntity legalEntity1 = new LegalEntity();
        legalEntity1.setName("LE1");
        request1.addLegalEntity(legalEntity1);

        Request request2 = new Request();
        request2.setName("Request 2");
        LegalEntity legalEntity2 = new LegalEntity();
        legalEntity2.setName("LE1");
        request2.addLegalEntity(legalEntity2);

        Request request3 = new Request();
        request3.setName("Request 3");
        LegalEntity legalEntity3 = new LegalEntity();
        legalEntity3.setName("LE1");
        request3.addLegalEntity(legalEntity3);

        // WHEN
        requestJpaRepository.save(Arrays.asList(request1, request2, request3));

        // THEN
        List<Request> requests = requestJpaRepository.findAll();

        assertThat(requests).isNotEmpty();
        assertThat(requests.size()).isEqualTo(3);

    }


//    @Test
//    @DatabaseSetup()

}
