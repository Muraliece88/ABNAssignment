package com.abn.dish.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.abn.dish.entitites.Dish;

@Repository
public interface RecipeRepo extends JpaRepository<Dish, Long>,JpaSpecificationExecutor<Dish> {


}
