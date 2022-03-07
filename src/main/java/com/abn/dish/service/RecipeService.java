package com.abn.dish.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abn.dish.dto.DishDto;
import com.abn.dish.dto.DishcreateDto;
import com.abn.dish.entitites.Dish;
import com.abn.dish.exception.RecipeNotFoundException;


public interface RecipeService {
	
	DishcreateDto createRecipe(DishcreateDto dishDto);
	
	DishDto updateRecipe(DishDto dishDto) throws RecipeNotFoundException;
	
	void deleteRecipeById(Long id) throws RecipeNotFoundException;
	
	 List<Dish> findByCriteria(LocalDateTime  date,boolean isVeg,
			 int suitableFor,List <String> ingredients,String instructions,Pageable pageable) throws RecipeNotFoundException;
	

}
