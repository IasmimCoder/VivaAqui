package com.ifpb.VivaAqui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ifpb.VivaAqui.model.Property;
import com.ifpb.VivaAqui.service.PropertyService;

import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    private PropertyService service;

    @PostMapping
    public Property addProperty(@RequestBody Property property) {
        return service.addProperty(property);
    }

    @GetMapping("/nearby")
    public List<Property> getNearbyProperties(@RequestParam double longitude, @RequestParam double latitude,
                                              @RequestParam double radiusKm) {
        return service.getNearbyProperties(longitude, latitude, radiusKm);
    }
}

