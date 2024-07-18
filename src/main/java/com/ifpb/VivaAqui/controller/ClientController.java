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

   @GetMapping("/getClient")
    public ResponseEntity<?> getClient(@RequestParam String cpf){
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

}
