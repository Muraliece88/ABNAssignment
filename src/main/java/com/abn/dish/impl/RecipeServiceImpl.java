package com.abn.dish.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abn.dish.constant.Constants;
import com.abn.dish.dto.DishDto;
import com.abn.dish.dto.DishcreateDto;
import com.abn.dish.entitites.Dish;
import com.abn.dish.entitites.Ingredients;
import com.abn.dish.exception.RecipeNotFoundException;
import com.abn.dish.repo.IngredientRepo;
import com.abn.dish.repo.RecipeRepo;
import com.abn.dish.service.RecipeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService {
	private final RecipeRepo recipeRepo;
	private final RecipeMappers mappers = Mappers.getMapper(RecipeMappers.class);


	@Transactional
	@Override
	public DishcreateDto createRecipe(DishcreateDto dishDto) {

		log.debug("Save recipe info{}" + dishDto.getClass());
		
		Dish dish = recipeRepo.save(mappers.INSTANCE.mapDishRequest(dishDto));
		return mappers.INSTANCE.mapDishResponse(dish);
		
	}

	@Transactional
	@Override
	public DishDto updateRecipe(DishDto dishDto) throws RecipeNotFoundException {
		log.debug("Find recipe id before update{}" + dishDto.getClass());

		recipeRepo.findById(dishDto.getId())
				.orElseThrow(() -> new RecipeNotFoundException(Constants.RECIPIE_ID_NOT_FOUND + dishDto.getId()));

		log.debug("Found receipe id to update {}" + dishDto.getClass());
		Dish dish =recipeRepo.save(mappers.INSTANCE.updateDishRequest(dishDto));
				return mappers.INSTANCE.updateDishResponse(dish); 
	}

	@Override
	public void deleteRecipeById(Long id) throws RecipeNotFoundException {
		log.debug("Find recipe id before deletion{}" + id);
		recipeRepo.findById(id).orElseThrow(() -> new RecipeNotFoundException(Constants.RECIPIE_ID_NOT_FOUND + id));
		log.debug("Delete receipe id {}" + id);
		recipeRepo.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Dish> findByCriteria(LocalDateTime date, boolean isVeg, int suitableFor, List<String> ingredients,
			String instructions, Pageable pageable)  {
		
		Page page = recipeRepo.findAll(new Specification<Dish>() {
			@Override
			public Predicate toPredicate(Root<Dish> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {			
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(criteriaBuilder.or(criteriaBuilder.greaterThanOrEqualTo(root.get("creationDateTime"), date)));
				predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("isVeg"), isVeg)));
				predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("suitableFor"), suitableFor)));
				predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("instructions"), "%" + instructions + "%")));
				predicates.add(criteriaBuilder.or(criteriaBuilder.trim((root.join("ingredients")).<String> get("name")).in(ingredients)));
				query.distinct(true);
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
		page.getTotalElements();
		page.getTotalPages();
		return page.getContent();
	}
	

		
	}


