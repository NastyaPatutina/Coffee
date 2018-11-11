package com.coffee.service.recipeIngredient;

import com.coffee.entity.RecipeIngredient;
import com.coffee.model.order.recipeIngredient.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface RecipeIngredientService {
    @Nonnull
    List<RecipeIngredientInfo> findAllRecipeIngredients();

    @Nullable
    RecipeIngredientInfo findRecipeIngredientById(@Nonnull Integer id);

    void deleteById(@Nonnull Integer id);

    RecipeIngredient save(RecipeMiniIngredientInfo userInfo);
}
