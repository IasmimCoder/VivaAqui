package com.ifpb.VivaAqui.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifpb.VivaAqui.model.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    
}

