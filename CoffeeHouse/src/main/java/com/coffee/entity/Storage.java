package com.coffee.entity;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


import javax.persistence.*;

@Entity
@Table(name = "storage")
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "count")
    private Integer count;

    @Column(name = "product_id")
    private Integer product_id;

    @Column(name = "house_id")
    private Integer house_id;

    public Integer getProductId() {
        return product_id;
    }

    public void setProductId(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getHouseId() {
        return house_id;
    }

    public void setHouseId(Integer house_id) {
        this.house_id = house_id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Storage storage = (Storage) o;

        return new EqualsBuilder()
                .append(id, storage.id)
                .append(count, storage.count)
                .append(product_id, storage.product_id)
                .append(house_id, storage.house_id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(count)
                .append(product_id)
                .append(house_id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("count", count)
                .add("product_id", product_id)
                .add("house_id", house_id)
                .toString();
    }
}
