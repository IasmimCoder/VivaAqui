package com.ifpb.VivaAqui.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ifpb.VivaAqui.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifpb.VivaAqui.exception.NotFoundException;
import com.ifpb.VivaAqui.exception.UnauthorizedException;
import com.ifpb.VivaAqui.repository.ClientRepository;
import com.ifpb.VivaAqui.repository.PropertyRepository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.resps.GeoRadiusResponse;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.args.GeoUnit;
import static java.lang.Math.*;

import com.ifpb.VivaAqui.exception.*;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository repository;

    @Autowired
    private ClientRepository clientRepository;
  
    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private Message message;

    public Property addProperty(Property property) {
        Client client = clientRepository.findById(property.getCpfOwner()).orElseThrow(
            () -> new NotFoundException("Cliente não encontrado")
        );
        client.getOfferedProperty().add(property);
        repository.save(property);
        //adicionar na lista de ofertadas do Cliente
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

    private boolean verificarCpf(String cpf, Long idProperty){
        Property property = getById(idProperty);
        if (cpf.equals(property.getCpfOwner())) {
            return true;
        }
        return false;
    }
    
    public Property updateStatusProperty(Long id, String cpf, String status){
        Property property = getById(id);
        if(verificarCpf(cpf, id)){

            EnumStatus statusAtualizado = EnumStatus.valueOf(status);
            property.setStatus(statusAtualizado);
            return repository.save(property);
        } else {
            throw new UnauthorizedException("Não autorizado");
        }
    }

    public Property updateProperty(Long id, String cpf, Property property) {
        Property propertyAtual = getById(id);
        if (!cpf.equals(propertyAtual.getCpfOwner())) {
            new UnauthorizedException("Não autorizado");
        }
        return repository.findById(id).map(existingProperty -> {
            existingProperty.setName(property.getName());
            existingProperty.setDescription(property.getDescription());
            existingProperty.setAddress(property.getAddress());
            existingProperty.setLongitude(property.getLongitude());
            existingProperty.setLatitude(property.getLatitude());
            existingProperty.setStatus(property.getStatus());
            repository.save(existingProperty);
            return existingProperty;
        }).orElseThrow(() -> new NotFoundException("Propriedade não encontrada"));
    }

    //

    public ResponseEntity<?> deletePropety(Long idProperty, String cpf){

        Optional<Property> optionalProperty = repository.findById(idProperty);
        Optional<Client> optionalClient = clientRepository.findById(cpf);

        if (!optionalProperty.isPresent()) {
            message.setMensagem("Propiedade não encontrada");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        else if (!optionalClient.isPresent()){
            message.setMensagem("Cliente não encontrado.");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        Client client = optionalClient.get();
        Property property = optionalProperty.get();
        if(!client.getOfferedProperty().contains(property)){
            message.setMensagem("Propriedade não pertence a esse cliente.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        repository.deleteById(idProperty);
        message.setMensagem("Propiedade removido com sucesso.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}