package com.ifpb.VivaAqui.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpb.VivaAqui.model.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}

