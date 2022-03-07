package com.abn.dish.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.abn.dish.dto.DishcreateDto;
import com.abn.dish.dto.IngredientsDto;
import com.abn.dish.entitites.Dish;
import com.abn.dish.exception.RecipeNotFoundException;
import com.abn.dish.impl.RecipeMappers;
import com.abn.dish.impl.RecipeServiceImpl;
import com.abn.dish.repo.RecipeRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RecipeControllerTest {
	 public static final String API_V1_DISH = "/api/v1/dish";
	    @Autowired
	    private MockMvc mockMvc;
	   
	    @Autowired
	    private RecipeMappers mappingDef = Mappers.getMapper(RecipeMappers.class);
	    private List<Dish> dishList=new ArrayList<>();;
	    @Mock
		private DishcreateDto dishcreate;
	    @Mock
		private IngredientsDto ingre;
	    private Set<IngredientsDto> ingsets = new HashSet();
	    @Mock
	    private ObjectMapper objectMapper;

		@Mock
		private RecipeServiceImpl mockservice;
		@Mock
		private Dish dish;
		@Autowired
		private RecipeRepo recipeRepo;
	    @Autowired
	    private WebApplicationContext context;
	    
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
			mockservice = new RecipeServiceImpl(recipeRepo);
			mockMvc= MockMvcBuilders
			          .webAppContextSetup(context)
			          .apply(SecurityMockMvcConfigurers.springSecurity())
			          .build();
	    }
	@Test
	void testCreateRecipe() throws JsonProcessingException, Exception {
		when(objectMapper.writeValueAsString(dishList.get(0))).thenReturn(anyString());
        mockMvc.perform(post(API_V1_DISH)
                        .content(objectMapper.writeValueAsString(dishList.get(0)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameOfDish", Matchers.is(anyString())));
    }
	

	@Test
	void testUpdateRecipe() throws Exception {
		dishList.get(0).setNameOfDish(anyString());
		when(objectMapper.writeValueAsString(dishList.get(0))).thenReturn(anyString());
        mockMvc.perform(post(API_V1_DISH)
                        .content(objectMapper.writeValueAsString(dishList.get(0)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameOfDish", Matchers.is(anyString())));

	}

	@Test
	void testDeleteRecipeById() throws Exception {
		 String dishId = dishList.get(0).getId().toString();
	        mockMvc.perform(delete(API_V1_DISH + dishId)
	                        .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());
		
	}
	@Test
	void testDeleteRecipeByIdFail() throws Exception {
		 long randomId = (long) (Math.random() * 100);
	        mockMvc.perform(delete(API_V1_DISH + randomId)
	                        .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(RecipeNotFoundException.class))
	                .andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage())
	                        .isEqualTo("Dish with id " + randomId + " does not exist"));
	}

	@Test
	void testGetRecipe() throws Exception {
		        mockMvc.perform(get(API_V1_DISH + "searchrecipe")
		                        .param("createdDate", "01‐01‐2022")
		                        .param("sVeg", "false")
		                        .param("suitableFor", "1")
		                        .param("ingredients", anyString())
		                        .param("instructions", anyString())
		                        .contentType(MediaType.APPLICATION_JSON)
		                        .accept(MediaType.APPLICATION_JSON))
		                .andExpect(status().isOk())
		                .andExpect(jsonPath("$.content[0].ingredients", Matchers.is(anyString())));
		
	}


}
