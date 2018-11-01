package com.coffee.entity;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "cost")
    private Integer cost;

    @OneToMany(mappedBy="recipes")
    private Set<RecipeIngredient> recipeIngredients;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Set<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Recipe order = (Recipe) o;

        return new EqualsBuilder()
                .append(id, order.id)
                .append(name, order.name)
                .append(cost, order.cost)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(cost)
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("name", name)
                .add("cost", cost)
                .toString();
    }
}
