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

    @Column(name = "customer_id")
    private Integer customerId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(name = "coffee_house_id")
    private Integer coffeeHouseId;

    public Order(Integer customerId, Recipe recipe, Integer coffeeHouseId) {
        this.customerId = customerId;
        this.recipe = recipe;
        this.coffeeHouseId = coffeeHouseId;
    }

    public Order(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customer_id) {
        this.customerId = customer_id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Integer getRecipeId() {
        return recipe.getId();
    }

    public Integer getCoffeeHouseId() {
        return coffeeHouseId;
    }

    public void setCoffeeHouseId(Integer coffee_house_id) {
        this.coffeeHouseId = coffee_house_id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return new EqualsBuilder()
                .append(id, order.id)
                .append(customerId, order.customerId)
                .append(recipe.getId(), order.recipe.getId())
                .append(coffeeHouseId, order.coffeeHouseId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(customerId)
                .append(recipe.hashCode())
                .append(coffeeHouseId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("customerId", customerId)
                .add("recipe", recipe.toString())
                .add("coffeeHouseId", coffeeHouseId)
                .toString();
    }
}
