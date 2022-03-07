
package com.abn.dish.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.abn.dish.dto.DishDto;
import com.abn.dish.dto.DishcreateDto;
import com.abn.dish.dto.IngredientsDto;
import com.abn.dish.entitites.Dish;
import com.abn.dish.exception.RecipeNotFoundException;
import com.abn.dish.repo.IngredientRepo;
import com.abn.dish.repo.RecipeRepo;

@SpringBootTest

@RunWith(MockitoJUnitRunner.class)

@ExtendWith(MockitoExtension.class)

@ActiveProfiles("test")

@Transactional
class RecipeServiceImplTest {

	private static List<Dish> dishList = new ArrayList();

	@Mock
	private RecipeRepo mockReciperepo;

	@Mock
	private DishcreateDto dishcreate;

	@Mock
	private Dish dish;
	@Mock
	private DishDto dishdto;

	@Mock
	private IngredientsDto ingre;
	private Set<IngredientsDto> ingsets = new HashSet();

	private RecipeMappers xMapper = Mappers.getMapper(RecipeMappers.class);

	@Mock
	private RecipeServiceImpl mockservice;

	@Captor
	private ArgumentCaptor<Dish> dishArgumentCaptor;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse("01-01-2022 09:00", formatter);
		dishcreate.setCreationDateTime(dateTime);
		dishcreate.setInstructions("heat");
		dishcreate.setDishName("anything");
		dishcreate.setSuitableFor(1);
		dishcreate.setVeg(false);
		ingre.setName("Onion");
		ingre.setName("Tomato");
		dishcreate.setIngredients(ingsets);
		dishList.add(dish);
		mockservice = new RecipeServiceImpl(mockReciperepo);
	}

	@Test

	@DisplayName("Create receipe")
	void testCreateRecipe() {

		when(mockReciperepo.save(Mockito.any(Dish.class))).thenReturn(dish);
		DishcreateDto returned = mockservice.createRecipe(dishcreate);
		Dish dish = xMapper.INSTANCE.mapDishRequest(returned);
		verify(mockReciperepo, times(1)).save(dishArgumentCaptor.capture());
		verifyNoMoreInteractions(mockReciperepo);
		assertEquals(dishcreate.getDishName(), dish.getNameOfDish());

	}

	@Test

	@DisplayName("updating receipe")
	void testUpdateRecipe() {

		Optional<Dish> returned = mockReciperepo.findById(Mockito.any(Long.class));

		verify(mockReciperepo, times(1)).findById(dish.getId());
		verifyNoMoreInteractions(mockReciperepo);

		assertEquals(dishdto, returned);
	}

	@Test

	@DisplayName("Delete receipe")
	void testDeleteRecipeById() {

	}

	@Test

	@DisplayName("find receipe by condition")
	void testFindByCriteria() {

	}

}
