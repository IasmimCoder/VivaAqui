package com.ifpb.VivaAqui.controller;

import com.ifpb.VivaAqui.model.Client;
import com.ifpb.VivaAqui.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService service;

   @PostMapping
    public ResponseEntity<?> addClient(@RequestBody Client client){
       return service.addClient(client);
   }


    @GetMapping("/{cpf}")
    public ResponseEntity<?> getClient(@PathVariable String cpf){
       return service.getClient(cpf);
    }

    @GetMapping("/findAllClients")
    public ResponseEntity<?> getAllClients(){
        return service.getAllClients();
    }

    @PostMapping("/addFavoriteProperty/{cpf}/{idProperty}")
    public ResponseEntity<?> addFavoriteProperty(@PathVariable String cpf, @PathVariable Long idProperty){
       return service.addFavoriteProperty(cpf, idProperty);
    }

    @PutMapping("/update/{cpf}/{novoNome}")
    public ResponseEntity<?> updateClient(@PathVariable String cpf, @PathVariable String novoNome){
       return service.updateClient(cpf, novoNome);
    }

    @DeleteMapping("/delete/{cpf}")
    public ResponseEntity<?> deleteClient(@PathVariable String cpf){
        return service.deleteCliente(cpf);
    }

    @PostMapping("/removeFavoriteProperty/{cpf}/{idProperty}")
    public ResponseEntity<?> removeFavoriteProperty(@PathVariable String cpf, @PathVariable Long idProperty){
        return service.removeFavoriteProperty(cpf, idProperty);
    }

}