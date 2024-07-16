package com.ifpb.VivaAqui.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String cpf;
    private String name;

    @OneToMany
    private List<Property> offeredProperty;

    @OneToMany
    private List<Property> favoriteProperty;

    @OneToMany
    private AreaDePesquisa areaDePesquisa;

    public Client() {
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

    public List<Property> getOfferedProperty() {
        return offeredProperty;
    }

    public void setOfferedProperty(List<Property> offeredProperty) {
        this.offeredProperty = offeredProperty;
    }

    public List<Property> getFavoriteProperty() {
        return favoriteProperty;
    }

    public void setFavoriteProperty(List<Property> favoriteProperty) {
        this.favoriteProperty = favoriteProperty;
    }

    public String toString() {
        return "Client{" +
                "cpf='" + cpf + '\'' +
                ", name='" + name + '\'' +
                ", offeredProperty=" + offeredProperty +
                ", favoriteProperty=" + favoriteProperty +
             //   ", areaDePesquisa=" + areaDePesquisa +
                '}';
    }
}
