package com.abn.dish.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.abn.dish.entitites.Ingredients;

@Repository
public interface IngredientRepo extends JpaRepository<Ingredients, Long>,JpaSpecificationExecutor<Ingredients> {



}
