package com.bnpp.cib.e2e.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class RequestSummaryDTO {
    private UUID id;
    private String name;
}
