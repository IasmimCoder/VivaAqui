package com.ifpb.VivaAqui.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifpb.VivaAqui.model.Property;
import com.ifpb.VivaAqui.repository.PropertyRepository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.params.GeoRadiusParam;
import redis.clients.jedis.resps.GeoRadiusResponse;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.args.GeoUnit;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository repository;
    @Autowired
    private Jedis jedis;

    @Autowired
    private JedisPool jedisPool;

      public Property addProperty(Property property) {
        repository.save(property);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.geoadd("properties", property.getLongitude(), property.getLatitude(), "property:" + property.getId());
        }
        return property;
    }

    public List<Property> getNearbyProperties(double longitude, double latitude, double radiusKm) {
        List<GeoRadiusResponse> geoResponses;
        try (Jedis jedis = jedisPool.getResource()) {
            geoResponses = jedis.geosearch("properties",
                    new GeoCoordinate(longitude, latitude), radiusKm, GeoUnit.KM);
        }
        List<Long> ids = geoResponses.stream()
                .map(response -> Long.valueOf(response.getMemberByString().split(":")[1]))
                .collect(Collectors.toList());
        return repository.findAllById(ids);
    }
}

