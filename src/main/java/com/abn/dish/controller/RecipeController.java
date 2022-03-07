package com.abn.dish.controller;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abn.dish.constant.Constants;
import com.abn.dish.dto.DishDto;
import com.abn.dish.dto.DishcreateDto;
import com.abn.dish.entitites.Dish;
import com.abn.dish.exception.RecipeNotFoundException;
import com.abn.dish.impl.RecipeMappers;
import com.abn.dish.service.RecipeService;
import com.google.gson.Gson;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Recipe Resource")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/dish", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RecipeController {

	private final RecipeService recipeService;
	 private static final Gson gson = new Gson();

	@Operation(summary = "Create a recipe")
	@PostMapping
	public ResponseEntity<DishcreateDto> createRecipe(@RequestBody @Valid DishcreateDto dishDto) {
		log.debug("Create recipe for {}", dishDto.getDishName());
		return ResponseEntity.ok(recipeService.createRecipe(dishDto));

	}

	@Operation(summary = "Update a recipe")
	@PutMapping
	 @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation =
     DishDto.class))), @ApiResponse(responseCode = "404", description = "Consultant not found", content
     = @Content(schema = @Schema(hidden = true)))})
	public ResponseEntity<DishDto> updateRecipe(@RequestBody @Valid DishDto dishDto) throws RecipeNotFoundException {
		log.debug("Update recipe {}", dishDto.getId());
		return ResponseEntity.ok(recipeService.updateRecipe(dishDto));

	}


	@DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteRecipeById(@PathVariable Long id) throws RecipeNotFoundException {
        log.debug("Delete consultant by id {}", id);
        recipeService.deleteRecipeById(id);
        return ResponseEntity.ok(gson.toJson(Constants.DISH_DELETE+id));
    }
	
	@Operation(summary = "Get recipes based any condition", description = "gets the recipe info based on different search params")
	@GetMapping(value = "/searchrecipe")
	public ResponseEntity<Page<DishDto>> getRecipe( @RequestParam(required=true) @DateTimeFormat(pattern="dd‐MM‐yyyy HH:mm") LocalDateTime  createdDate,
			@RequestParam(required=true) boolean isVeg,
			@RequestParam(required=true) int suitableFor,
			@RequestParam(required= true) List <String> ingredients,
			@RequestParam(required=true) String instructions,
			@ParameterObject Pageable pageable) throws RecipeNotFoundException {

		log.debug("Get a recipe {}", "");
		List<Dish> dishList=recipeService.findByCriteria(createdDate, isVeg, suitableFor, ingredients, instructions, pageable);
		final int start = (int)pageable.getOffset();
		final int end = Math.min((start + pageable.getPageSize()), dishList.size());
		final Page<Dish> dishPage = new PageImpl<>(dishList.subList(start, end), pageable, dishList.size());
		return ResponseEntity.ok(RecipeMappers.INSTANCE.toPageableresp(dishPage));
	

	}
	
}
