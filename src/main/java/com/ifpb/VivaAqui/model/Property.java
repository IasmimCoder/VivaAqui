package com.ifpb.VivaAqui.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String address;
    @Column(name = "longitude", columnDefinition = "DOUBLE PRECISION")
    private double longitude;
    @Column(name = "latitude", columnDefinition = "DOUBLE PRECISION")
    private double latitude;

    @Enumerated(EnumType.STRING)
    private EnumStatus status;

    private String cpfOwner;

    public Property() {
    }

    public Property(Long id, String name, String description, String address, double longitude, double latitude, EnumStatus status, String owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;
        this.cpfOwner = owner;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EnumStatus getStatus() {
        return this.status;
    }

    public void setStatus(EnumStatus status) {
        this.status = status;
    }

    public String getCpfOwner() {
        return this.cpfOwner;
    }

    public void setCpfOwner(String cpfOwner) {
        this.cpfOwner = cpfOwner;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", address='" + getAddress() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", status='" + getStatus() + "'" +
            ", cpfOwner='" + getCpfOwner() + "'" +
            "}";
    }
}
