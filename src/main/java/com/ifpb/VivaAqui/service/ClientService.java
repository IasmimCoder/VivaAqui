package com.ifpb.VivaAqui.service;

import com.ifpb.VivaAqui.model.Client;
import com.ifpb.VivaAqui.model.Message;
import com.ifpb.VivaAqui.model.Property;
import com.ifpb.VivaAqui.repository.ClientRepository;
import com.ifpb.VivaAqui.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private Message message;


    public ResponseEntity<?> addClient(Client client){

        if (client.getName().equals("")|| client.getCpf().equals("")){
            message.setMensagem("Os valores não podem ser vazios");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(clientRepository.save(client), HttpStatus.CREATED);
    }

    public ResponseEntity<?> getClient(String cpf){

        Optional<Client> optional = clientRepository.findById(cpf);

        if (optional.isPresent()){
            return new ResponseEntity<>(optional.get(), HttpStatus.FOUND);
        }
        message.setMensagem("Cliente não encontrado");

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    //Metodo para buscar todos os Clientes
    public  ResponseEntity<?> getAllClients(){

        return new ResponseEntity<>(clientRepository.findAll(), HttpStatus.OK);
    }

    //Metodo para adicionar propriedade aos favoritos de um cliente
    public ResponseEntity<?> addFavoriteProperty(String cpf, Long idProperty){

        Optional<Client> optionalClient = clientRepository.findById(cpf);

        Optional<Property> optionalProperty = propertyRepository.findById(idProperty);

        if (!optionalClient.isPresent()){
            message.setMensagem("Cliente não encontrado");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (!optionalProperty.isPresent()) {
            message.setMensagem("Propiedade não encontrada");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        Client client = optionalClient.get();
        Property property = optionalProperty.get();

        client.getFavoriteProperty().add(property);

        return new ResponseEntity<>(clientRepository.save(client), HttpStatus.CREATED);
    }

}
