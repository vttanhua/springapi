package com.example.sa.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.example.sa.dto.Login;
import com.example.sa.entity.Client;
import com.example.sa.service.ClientService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/client")
public class ClientController {
  /* 
    @Autowired
    private ClientService clientService;
    
    public ClientController(ClientService clientService) {
    	this.clientService = clientService;
    }

    @PostMapping("/signin")
    public String login(@RequestBody @Valid Login loginDto) {
       return clientService.signin(loginDto.getClientId(), loginDto.getClientSecret()).orElseThrow(()->
               new HttpServerErrorException(HttpStatus.FORBIDDEN, "Login Failed"));
    }

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Client signup(@RequestBody @Valid Login loginDto){
        return clientService.signup(loginDto.getClientId(), loginDto.getClientSecret(), loginDto.getName()).orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST,"Client already exists"));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Client> getAllUsers() {
        return clientService.getAll();
    }
    */

}