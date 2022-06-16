package com.example.sa.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sa.entity.Role;
import com.example.sa.entity.Client;
import com.example.sa.repository.RoleRepository;
import com.example.sa.repository.ClientRepository;
import com.example.sa.security.JwtProvider;
import com.example.sa.service.ClientService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClientService {
/*
    private ClientRepository clientRepository;

    private AuthenticationManager authenticationManager;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private JwtProvider jwtProvider;

    @Autowired
    public ClientService(ClientRepository clientRepository, AuthenticationManager authenticationManager,
                       RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.clientRepository = clientRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    /*
     * Sign in a user into the application, with JWT-enabled authentication
     *
     * @param username  username
     * @param password  password
     * @return Optional of the Java Web Token, empty otherwise
     *
    public Optional<String> signin(String clientId, String clientSecret) {
        log.info("New Client attempting to sign in");
        Optional<String> token = Optional.empty();
        Optional<Client> client = clientRepository.findByClientId(clientId);
        if (client.isPresent()) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(clientId, clientSecret));
                token = Optional.of(jwtProvider.createToken(clientId, client.get().getRoles()));
            } catch (AuthenticationException e){
                log.info("Log in failed for client {}", clientId);
            }
        }
        return token;
    }

    public Optional<Client> signup(String clientId, String clientSecret, String name) {
        log.info("New user attempting to sign in");
        Optional<Client> client = Optional.empty();
        if (!clientRepository.findByClientId(clientId).isPresent()) {
            Optional<Role> role = roleRepository.findByRoleName("ROLE_CSR");
            client = Optional.of(clientRepository.save(new Client(clientId,
                            passwordEncoder.encode(clientSecret),
                            role.get(),
                            name)));
        }
        return client;
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }
    **/
}
