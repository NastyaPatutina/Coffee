package com.coffee.service.recipe;

import com.coffee.entity.Recipe;
import com.coffee.helpers.Builder;
import com.coffee.model.order.recipe.*;
import com.coffee.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<RecipeWithIngredientsInfo> findAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(Builder::buildRecipeInfoWithIngredients)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    public RecipeWithIngredientsInfo findRecipeById(@Nonnull Integer id) {
        return recipeRepository.findById(id).map(Builder::buildRecipeInfoWithIngredients).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        recipeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Recipe save(RecipeInfo recipeInfo) {
        return recipeRepository.save(Builder.buildRecipeByInfo(recipeInfo));
    }
}
