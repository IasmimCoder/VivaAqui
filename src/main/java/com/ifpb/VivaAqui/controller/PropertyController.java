package com.ifpb.VivaAqui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import com.ifpb.VivaAqui.model.Client;
import com.ifpb.VivaAqui.model.EnumStatus;
import com.ifpb.VivaAqui.model.Property;
import com.ifpb.VivaAqui.model.PropertyDistance;
import com.ifpb.VivaAqui.service.PropertyService;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    private PropertyService service;

    @PostMapping
    public Property addProperty(@RequestBody Property property, String owner) {
        return service.addProperty(property, owner);
    }

    @GetMapping
    public List<Property> getAllProperties() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Property getPropertyById(@PathVariable Long id) {
        return service.getById(id);
    }

    
    // @GetMapping("/nearby")
    // public Page<PropertyDistance> getNearbyProperties(@PageableDefault(page = 0, size = 4, sort = "distance", direction = Sort.Direction.ASC) Pageable pageable) {
    //     return service.getAllProperties(pageable);
    // }

    @GetMapping("/nearby")
    public List<PropertyDistance> getNearbyProperties(@RequestParam double longitude, @RequestParam double latitude,
                                              @RequestParam double radiusKm) {
        return service.getNearbyProperties(longitude, latitude, radiusKm);
    }

    @PutMapping("/{id}")
    public Property updateStatus(@PathVariable Long id, @RequestBody String cpf, EnumStatus enumStatus) {
        return service.updateStatusProperty(id, cpf, enumStatus);
    }
}

