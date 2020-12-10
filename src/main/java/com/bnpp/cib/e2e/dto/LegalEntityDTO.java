package com.bnpp.cib.e2e.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LegalEntityDTO {
    private UUID id;
    private String name;
}
