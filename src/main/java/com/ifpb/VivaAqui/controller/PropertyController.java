package com.ifpb.VivaAqui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ifpb.VivaAqui.model.Property;
import com.ifpb.VivaAqui.model.PropertyDistance;
import com.ifpb.VivaAqui.service.PropertyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    private PropertyService service;

    @PostMapping
    public Property addProperty(@RequestBody Property property) {
        return service.addProperty(property);
    }

    @GetMapping
    public List<Property> getAllProperties() {
        return service.getAllProperties();
    }

    @GetMapping("/{id}")
    public Optional<Property> getPropertyById(@PathVariable Long id) {
        return service.getPropertyById(id);
    }

    @GetMapping("/nearby")
    public List<PropertyDistance> getNearbyProperties(@RequestParam double longitude, @RequestParam double latitude,
                                              @RequestParam double radiusKm) {
        return service.getNearbyProperties(longitude, latitude, radiusKm);
    }
}

