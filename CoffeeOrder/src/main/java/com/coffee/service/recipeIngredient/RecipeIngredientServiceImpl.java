package com.coffee.service.recipeIngredient;
import com.coffee.entity.RecipeIngredient;
import com.coffee.model.RecipeIngredientInfo;
import com.coffee.repository.RecipeIngredientRepository;
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

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<RecipeIngredientInfo> findAllRecipeIngredients() {
        return recipeIngredientRepository.findAll()
                .stream()
                .map(this::buildRecipeIngredientInfo)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    public RecipeIngredientInfo findRecipeIngredientById(@Nonnull Integer id) {
        return recipeIngredientRepository.findById(id).map(this::buildRecipeIngredientInfo).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull Integer id) {
        recipeIngredientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RecipeIngredient save(RecipeIngredientInfo recipeIngredientInfo) {
        return recipeIngredientRepository.save(buildRecipeIngredientByInfo(recipeIngredientInfo));
    }

    @Nonnull
    private RecipeIngredientInfo buildRecipeIngredientInfo(RecipeIngredient recipeIngredient) {
        RecipeIngredientInfo info = new RecipeIngredientInfo();
        info.setId(recipeIngredient.getId());
        info.setProductId(recipeIngredient.getProductId());
        info.setRecipeId(recipeIngredient.getRecipeId());
        info.setCount(recipeIngredient.getCount());
        return info;
    }

    @Nonnull
    private RecipeIngredient buildRecipeIngredientByInfo(RecipeIngredientInfo recipeIngredientInfo) {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(recipeIngredientInfo.getId()).orElse(null);
        recipeIngredient.setProductId(recipeIngredientInfo.getProductId());
        recipeIngredient.setRecipeId(recipeIngredientInfo.getRecipeId());
        recipeIngredient.setCount(recipeIngredientInfo.getCount());
        return recipeIngredient;
    }
}
