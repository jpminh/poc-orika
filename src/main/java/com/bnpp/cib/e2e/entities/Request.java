package com.bnpp.cib.e2e.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Table
@Entity
public class Request {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column
    private String name;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private Set<LegalEntity> legalEntities = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<LegalEntity> getLegalEntities() {
        return legalEntities;
    }

    public void setLegalEntities(Set<LegalEntity> legalEntities) {
        this.legalEntities = legalEntities;
    }

    public void addLegalEntity(LegalEntity legalEntity) {
        this.legalEntities.add(legalEntity);
        legalEntity.setRequest(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id) &&
                Objects.equals(name, request.name) &&
                Objects.equals(legalEntities, request.legalEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, legalEntities);
    }
}
