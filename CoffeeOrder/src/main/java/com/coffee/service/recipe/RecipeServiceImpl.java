package com.coffee.service.recipe;

import com.coffee.entity.Order;
import com.coffee.entity.Recipe;
import com.coffee.entity.RecipeIngredient;
import com.coffee.helpers.Builder;
import com.coffee.model.order.recipe.*;
import com.coffee.model.order.recipeIngredient.OnlyIngredientInfo;
import com.coffee.repository.RecipeIngredientRepository;
import com.coffee.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

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
        Recipe recipe;
        try {
            recipe = recipeRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Recipe not found", ex);
        }
        return Builder.buildRecipeInfoWithIngredients(recipe);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        Recipe recipe;
        try {
            recipe = recipeRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Recipe not found", ex);
        }

        for (RecipeIngredient oiInfo : recipe.getRecipeIngredients()) {
            recipeIngredientRepository.deleteById(oiInfo.getId());
        }
        recipeRepository.delete(recipe);
    }

    @Override
    @Transactional
    public Recipe save(RecipeWithIngredientsInfo recipeInfo) {
        Recipe recipe;
        try {
            recipe = recipeRepository.save(Builder.buildRecipeByInfo(recipeInfo));
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE,
                    "Error save recipe: " + ex.getCause().getCause().getMessage(), ex);
        }

        for (OnlyIngredientInfo oiInfo : recipeInfo.getRecipeIngredients()) {
            try {
            recipe.addRecipeIngredients(recipeIngredientRepository.save(Builder.buildRecipeMiniIngredientInfo(oiInfo, recipe)));
            } catch (Exception ex) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_ACCEPTABLE,
                        "Error save recipe ingredient for product " + oiInfo.getProductId() + ": " + ex.getCause().getCause().getMessage(), ex);
            }

        }
        return recipe;
    }

    @Override
    @Transactional
    public Recipe save(RecipeInfo recipeInfo) {
        Recipe recipe;
        try {
            recipe = recipeRepository.save(Builder.buildRecipeByInfo(recipeInfo));
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE,
                    "Error save recipe: " + ex.getCause().getCause().getMessage(), ex);
        }
        return recipe;
    }
}
