package com.coffee.repository;

import com.coffee.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository  extends JpaRepository<Recipe, Integer> {
}

