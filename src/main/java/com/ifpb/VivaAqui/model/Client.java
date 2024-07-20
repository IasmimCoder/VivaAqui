package com.ifpb.VivaAqui.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tb_client")
public class Client {

    @Id
    private String cpf;
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.REMOVE)
    @JoinColumn(name = "offeredProperty")
    private Set<Property> offeredProperty;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "favoriteProperty")
    private Set<Property> favoriteProperty;

    public Client() {
    }

    public Client(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Property> getOfferedProperty() {
        return offeredProperty;
    }

    public void setOfferedProperty(Set<Property> offeredProperty) {
        this.offeredProperty = offeredProperty;
    }

    public Set<Property> getFavoriteProperty() {
        return favoriteProperty;
    }

    public void setFavoriteProperty(Set<Property> favoriteProperty) {
        this.favoriteProperty = favoriteProperty;
    }

    public String toString() {
        return "Client{" +
                "cpf='" + cpf + '\'' +
                ", name='" + name + '\'' +
                ", offeredProperty=" + offeredProperty +
                ", favoriteProperty=" + favoriteProperty;
    }
}
