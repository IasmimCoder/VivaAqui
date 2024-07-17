package com.ifpb.VivaAqui.service;

import ch.qos.logback.core.joran.conditional.ElseAction;
import com.ifpb.VivaAqui.model.Client;
import com.ifpb.VivaAqui.model.Message;
import com.ifpb.VivaAqui.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;
    @Autowired
    private Message message;


    public ResponseEntity<?> addClient(Client client){
        if (client.getName().equals("")|| client.getCpf().equals("")){
            message.setMensagem("Os valores não podem ser vazios");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(repository.save(client), HttpStatus.CREATED);
    }

    public ResponseEntity<?> getClient(String cpf){
        Optional<Client> optional = repository.findById(cpf);
        if (optional.isPresent()){
            return new ResponseEntity<>(optional.get(), HttpStatus.FOUND);
        }
        message.setMensagem("Cliente não encontrado");
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    //Metodo para buscar todos os Clientes
    public  ResponseEntity<?> getAllClients(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }



}
