package com.coffee.service.recipe;

import com.coffee.entity.Recipe;
import com.coffee.model.RecipeInfo;
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
    public List<RecipeInfo> findAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(this::buildRecipeInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    public RecipeInfo findRecipeById(@Nonnull Integer id) {
        return recipeRepository.findById(id).map(this::buildRecipeInfo).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        recipeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Recipe save(RecipeInfo recipeInfo) {
        return recipeRepository.save(buildRecipeByInfo(recipeInfo));
    }

    @Nonnull
    private RecipeInfo buildRecipeInfo(Recipe recipe) {
        RecipeInfo info = new RecipeInfo();
        info.setId(recipe.getId());
        info.setName(recipe.getName());
        info.setCost(recipe.getCost());
        return info;
    }

    @Nonnull
    private Recipe buildRecipeByInfo(RecipeInfo recipeInfo) {
        Recipe recipe = recipeRepository.findById(recipeInfo.getId()).orElse(null);
        recipe.setName(recipeInfo.getName());
        recipe.setCost(recipeInfo.getCost());
        return recipe;
    }
}
