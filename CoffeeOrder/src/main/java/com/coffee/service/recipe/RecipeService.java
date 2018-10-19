package com.coffee.service.recipe;

import com.coffee.entity.Recipe;
import com.coffee.model.RecipeInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface RecipeService {
    @Nonnull
    List<RecipeInfo> findAllRecipes();

    @Nullable
    RecipeInfo findRecipeById(@Nonnull Integer id);

    void deleteById(@Nonnull Integer id);

    Recipe save(RecipeInfo userInfo);
}
