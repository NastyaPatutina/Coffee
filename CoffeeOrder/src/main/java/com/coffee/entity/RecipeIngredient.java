package com.coffee.entity;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


import javax.persistence.*;
import javax.print.attribute.IntegerSyntax;

@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id")
    private Integer product_id;


    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @ManyToOne
    @JoinColumn(name="recipe_id", nullable=false)
    private Recipe recipe;

    @Column(name = "count")
    private Integer count;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getProductId() {
        return product_id;
    }

    public void setProductId(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getRecipeId() {
        return recipe.getId();
    }

    public void setRecipeId(Integer recipe_id) {
        this.recipe.setId(recipe_id);
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RecipeIngredient recipeIngredient = (RecipeIngredient) o;

        return new EqualsBuilder()
                .append(id, recipeIngredient.id)
                .append(product_id, recipeIngredient.product_id)
                .append(recipe.getId(), recipeIngredient.recipe.getId())
                .append(count, recipeIngredient.count)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(product_id)
                .append(recipe.getId())
                .append(count)
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("product_id", product_id)
                .add("recipe_id", recipe.getId())
                .add("count", count)
                .toString();
    }
}
