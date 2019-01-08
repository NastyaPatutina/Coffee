package com.coffee.service.recipeIngredient;
import com.coffee.entity.RecipeIngredient;
import com.coffee.helpers.Builder;
import com.coffee.model.order.recipeIngredient.*;
import com.coffee.repository.RecipeIngredientRepository;
import com.coffee.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<RecipeIngredientInfo> findAllRecipeIngredients() {
        return recipeIngredientRepository.findAll()
                .stream()
                .map(Builder::buildRecipeIngredientInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    public RecipeIngredientInfo findRecipeIngredientById(@Nonnull Integer id) {
        return recipeIngredientRepository.findById(id).map(Builder::buildRecipeIngredientInfo).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        recipeIngredientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RecipeIngredient save(RecipeMiniIngredientInfo recipeMiniIngredientInfo) {
        return recipeIngredientRepository.save(Builder.buildRecipeIngredientByMiniInfo(recipeMiniIngredientInfo,
                recipeRepository.findById(recipeMiniIngredientInfo.getRecipeId()).orElse(null)));
    }
}
