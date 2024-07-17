package com.ifpb.VivaAqui.repository;

import com.ifpb.VivaAqui.model.Client;
import com.ifpb.VivaAqui.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
}
