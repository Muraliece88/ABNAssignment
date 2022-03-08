
package com.abn.dish.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import com.abn.dish.dto.DishDto;
import com.abn.dish.dto.DishcreateDto;
import com.abn.dish.dto.IngredientsDto;
import com.abn.dish.entitites.Dish;
import com.abn.dish.entitites.Ingredients;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecipeMappers {
	RecipeMappers INSTANCE = Mappers.getMapper(RecipeMappers.class);

	@Mapping(source = "nameOfDish", target = "dishName")
	DishcreateDto mapDishResponse(Dish dish);

	@Mapping(source = "dishName", target = "nameOfDish")
	Dish mapDishRequest(DishcreateDto dish);

	@Mapping(source = "dishName", target = "nameOfDish")
	Dish updateDishRequest(DishDto dish);

	@Mapping(source = "nameOfDish", target = "dishName")
	DishDto updateDishResponse(Dish dish);

	Ingredients mapIngrRequest(IngredientsDto ingdto);

	default Page<DishDto> toPageableresp(Page<Dish> dish) {
		return dish.map(this::toGetResponseBody);
	}

	@Mapping(source = "nameOfDish", target = "dishName")
	DishDto toGetResponseBody(Dish dish);

}
