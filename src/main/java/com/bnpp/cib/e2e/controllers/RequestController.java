package com.bnpp.cib.e2e.controllers;

import com.bnpp.cib.e2e.dto.RequestDTO;
import com.bnpp.cib.e2e.dto.RequestSummaryDTO;
import com.bnpp.cib.e2e.entities.Request;
import com.bnpp.cib.e2e.mappers.OrikaBeanMapper;
import com.bnpp.cib.e2e.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private OrikaBeanMapper mapper;

    @Autowired
    private RequestService requestService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequestSummaryDTO> getRequests() {
        return mapper.mapAsList(requestService.getRequests(), RequestSummaryDTO.class);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestDTO getRequest(@PathVariable UUID id) {
        return mapper.map(requestService.getRequest(id), RequestDTO.class);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestDTO createRequest(@RequestBody RequestDTO requestDTO) {
        Request request = mapper.map(requestDTO, Request.class);
        Request createdRequest = requestService.createRequest(request);
        return mapper.map(createdRequest, RequestDTO.class);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestDTO updateRequest(@PathVariable UUID id, @RequestBody RequestDTO requestDTO) {
        Request request = mapper.map(requestDTO, Request.class);
        Request updatedRequest = requestService.updateRequest(id, request);
        return mapper.map(updatedRequest, RequestDTO.class);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteRequest(@PathVariable UUID id) {
        requestService.deleteRequest(id);
    }

}
