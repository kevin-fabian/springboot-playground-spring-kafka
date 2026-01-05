package com.fabiankevin.springkafkatransactional.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlanetRepository extends JpaRepository<PlanetEntity, UUID> {
}
