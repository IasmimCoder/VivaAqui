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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    //OK
    //Metodo para cliar cliente (cpf e nome)
    public ResponseEntity<?> addClient(Client client){

        if (client.getName().equals("")|| client.getCpf().equals("")){
            message.setMensagem("Os valores não podem ser vazios");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else if ((clientRepository.findById(client.getCpf())).isPresent()) {
            message.setMensagem("Esse CPF já foi cadastrado");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(clientRepository.save(client), HttpStatus.CREATED);
    }

    //ok
    public ResponseEntity<?> getClient(String cpf){

        Optional<Client> optional = clientRepository.findById(cpf);

        if (optional.isPresent()){
            return new ResponseEntity<>(optional.get(), HttpStatus.FOUND);
        }
        message.setMensagem("Cliente não encontrado");

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    //ok
    //Metodo para buscar todos os Clientes
    public  ResponseEntity<?> getAllClients(){

        return new ResponseEntity<>(clientRepository.findAll(), HttpStatus.OK);
    }

    //ok
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

    //ok
    //Metodo para atulizar nome do cliente
    public ResponseEntity<?> updateClient(String cpf, String novoNome){
        if (novoNome.equals("")|| cpf.equals("")){
            message.setMensagem("Os valores não podem ser vazios");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        Optional<Client> optionalClient = clientRepository.findById(cpf);
        if (!optionalClient.isPresent()){
            message.setMensagem("CPF não encontrado");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        Client client = optionalClient.get();
        client.setName(novoNome);
        return new ResponseEntity<>(clientRepository.save(client), HttpStatus.OK);
    }

    //ok
    //Metodo para remover Cliente
    public ResponseEntity<?> deleteCliente(String cpf){
        if (cpf.equals("")){
            message.setMensagem("Os valores não podem ser vazios.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        Optional<Client> optional = clientRepository.findById(cpf);
        if (!optional.isPresent()){
            message.setMensagem("Cliente não encontrado.");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        //fazer while para deletar todas as propriedades da lista
        clientRepository.deleteById(cpf);
        message.setMensagem("Cliente removido com sucesso.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //ok
    //Metodo para remover propriedade favorita da lista
    public ResponseEntity<?> removeFavoriteProperty(String cpf, Long idProperty){

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

        if (!client.getFavoriteProperty().contains(property)) {
            message.setMensagem("Propiedade não está na lista de Favoritos.");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        client.getFavoriteProperty().remove(property);

        return new ResponseEntity<>(clientRepository.save(client), HttpStatus.OK);
    }

}
