package com.coffee.entity;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


import javax.persistence.*;

@Entity
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 40)
    private String name;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "longitude")
    private float longitude;

    @Column(name = "latitude")
    private float latitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        House house = (House) o;

        return new EqualsBuilder()
                .append(id, house.id)
                .append(name, house.name)
                .append(address, house.address)
                .append(longitude, house.longitude)
                .append(latitude, house.latitude)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(address)
                .append(longitude)
                .append(latitude)
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("name", name)
                .add("address", address)
                .add("longitude", longitude)
                .add("latitude", latitude)
                .toString();
    }
}
