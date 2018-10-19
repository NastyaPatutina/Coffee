package com.coffee.entity;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "recipe_id")
    private Integer recipe_id;

    @Column(name = "coffee_house_id")
    private Integer coffee_house_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getRecipeId() {
        return recipe_id;
    }

    public void setRecipeId(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }

    public Integer getCoffeeHouseId() {
        return coffee_house_id;
    }

    public void setCoffeeHouseId(Integer coffee_house_id) {
        this.coffee_house_id = coffee_house_id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return new EqualsBuilder()
                .append(id, order.id)
                .append(user_id, order.user_id)
                .append(recipe_id, order.recipe_id)
                .append(coffee_house_id, order.coffee_house_id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(user_id)
                .append(recipe_id)
                .append(coffee_house_id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("user_id", user_id)
                .add("recipe_id", recipe_id)
                .add("coffee_house_id", coffee_house_id)
                .toString();
    }
}
