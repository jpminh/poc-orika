package com.bnpp.cib.e2e.controllers;

import com.bnpp.cib.e2e.services.JpaModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/model")
public class JpaModelController {

    @Autowired
    private JpaModelService jpaModelService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getJpaModels() throws NoSuchFieldException {
        return jpaModelService.getJpaModels();
    }
}
