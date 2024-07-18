package com.ifpb.VivaAqui.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifpb.VivaAqui.exception.NotFoundException;
import com.ifpb.VivaAqui.exception.UnauthorizedException;
import com.ifpb.VivaAqui.model.Client;
import com.ifpb.VivaAqui.model.EnumStatus;
import com.ifpb.VivaAqui.model.Property;
import com.ifpb.VivaAqui.model.PropertyDistance;
import com.ifpb.VivaAqui.repository.ClientRepository;
import com.ifpb.VivaAqui.repository.PropertyRepository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.resps.GeoRadiusResponse;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.args.GeoUnit;
import static java.lang.Math.*;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository repository;

    @Autowired
    private ClientRepository clientRepository;
  
    @Autowired
    private JedisPool jedisPool;

    public Property addProperty(Property property, String cpf) {
        Client client = clientRepository.findById(cpf).orElseThrow(
            () -> new NotFoundException("Cliente não encontrado")
        );
        property.setOwner(client);
        repository.save(property);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.geoadd("properties", property.getLongitude(), property.getLatitude(), "property:" + property.getId());
        }
        return property;
    }

    public List<PropertyDistance> getNearbyProperties(double longitude, double latitude, double radiusKm) {
        List<GeoRadiusResponse> geoResponses;
        try (Jedis jedis = jedisPool.getResource()) {
            geoResponses = jedis.geosearch("properties",
                    new GeoCoordinate(longitude, latitude), radiusKm, GeoUnit.KM);
        }
        List<Long> ids = geoResponses.stream()
                .map(response -> Long.valueOf(response.getMemberByString().split(":")[1]))
                .collect(Collectors.toList());

        List<PropertyDistance> propertyDistances = new ArrayList<PropertyDistance>();
        for (Long id : ids) {
            Property property = repository.findById(id).get();
            double longitude2 = property.getLongitude();
            double latitude2 = property.getLatitude();
            double distance = calcularDistancia(latitude, longitude, latitude2, longitude2);

            double distanceCortado = Math.floor(distance * 10) / 10; // Corta para uma casa decimal

            PropertyDistance propertyDistance = new PropertyDistance(property, distanceCortado);
            propertyDistances.add(propertyDistance);
        }
        return propertyDistances;
    }

    public static double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Raio da Terra em Km
        double latDistance = toRadians(lat2 - lat1);
        double lonDistance = toRadians(lon2 - lon1);
        double a = sin(latDistance / 2) * sin(latDistance / 2)
                + cos(toRadians(lat1)) * cos(toRadians(lat2))
                * sin(lonDistance / 2) * sin(lonDistance / 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));
        return R * c; // Retorna a distância em Km
    }

    public List<Property> getAll() {
        return repository.findAll();
    }

    public Property getById(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new NotFoundException("Propriedade não encontrada")
        );
    }

    public Property updateStatusProperty(Long id, String cpf, EnumStatus enumStatus){
        Property property = getById(id);
        if (!cpf.equals(property.getOwner().getCpf())) {
            new UnauthorizedException("Não autorizado");
        }
        property.setStatus(enumStatus);
        return repository.save(property);
    }
}