package com.example.sa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "security_client")
public class Client extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Client(String clientId, String clientSecret, Role role, String name) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.roles = Arrays.asList(role);
        this.name = name;
    }

    @Column(name = "clientid")
    private String clientId;

    @Column(name = "clientsecret")
    @JsonIgnore
    private String clientSecret;

    @Column(name = "name")
    private String name;

    @Column
    private Boolean accountExpired;
    
    @Column
    private Boolean accountLocked;
    
    @Column
    private Boolean credentialsExpired;
    
    @Column
    private Boolean disabled;
    /**
     * Default Constructor.
     */
    protected Client() {
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "client_role", joinColumns
            = @JoinColumn(name = "client_id",
            referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    referencedColumnName = "id"))


    private List<Role> roles;


}
