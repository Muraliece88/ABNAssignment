package com.abn.dish.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.abn.dish.dto.DishcreateDto;
import com.abn.dish.dto.IngredientsDto;
import com.abn.dish.entitites.Dish;
import com.abn.dish.exception.RecipeNotFoundException;
import com.abn.dish.impl.RecipeMappers;
import com.abn.dish.impl.RecipeServiceImpl;
import com.abn.dish.repo.RecipeRepo;
import com.abn.dish.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RecipeControllerTest {
	public static final String API_V1_DISH = "/api/v1/dish/";
	@Autowired
	private MockMvc mockMvc;

	private RecipeMappers mappingDef = Mappers.getMapper(RecipeMappers.class);
	@Mock
	private IngredientsDto ingre;
	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private RecipeServiceImpl mockservice;

	@Autowired
	private RecipeRepo recipeRepo;
	@Autowired
	private WebApplicationContext context;
	 private List<Dish> dishList;
	 
	 

	@BeforeEach
	void setUp() {
		dishList = Utils.loadData(Dish.class);
	        Optional.of(dishList)
	                .ifPresent(cs -> dishList = recipeRepo.saveAll(cs));
		MockitoAnnotations.openMocks(this);
		mockservice = new RecipeServiceImpl(recipeRepo);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity())
				.build();

	}
/**
 * Test to check if receipe is created from controller
 * @throws JsonProcessingException
 * @throws Exception
 */
	@Test
	@WithMockUser(username = "dummy", roles = "user")
	void testCreateRecipe() throws JsonProcessingException, Exception {
		String content = "{\r\n" + "  \"dishName\": \"Soup\",\r\n" + "  \"creationDateTime\": \"01‐01‐2022 09:00\",\r\n"
				+ "  \"suitableFor\": 1,\r\n" + "  \"instructions\": \"Boil water add vegetables and heat \",\r\n"
				+ "  \"ingredients\": [\r\n" + "\r\n" + "{\r\n" + " \"name\": \"Carrot\"\r\n" + "}\r\n" + ",\r\n"
				+ "{\r\n" + " \"name\": \"Tomato\"\r\n" + "}\r\n" + ",\r\n" + "{\r\n" + " \"name\": \"Water\"\r\n"
				+ "}\r\n" + "  ],\r\n" + "  \"id\": 0,\r\n" + "  \"veg\": false\r\n" + "}";

		mockMvc.perform(post(API_V1_DISH).content(content).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")).andExpect(status().isOk());
	}
	/**
	 * Test to check if receipe is updated from controller
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	@Test

	@WithMockUser(username = "dummy", roles = "user")
	void testUpdateRecipe() throws Exception {
		String content = "{\r\n" + "  \"dishName\": \"Soup\",\r\n" + "  \"creationDateTime\": \"01‐01‐2022 09:00\",\r\n"
				+ "  \"suitableFor\": 1,\r\n" + "  \"instructions\": \"Boil water add vegetables and heat \",\r\n"
				+ "  \"ingredients\": [\r\n" + "\r\n" + "{\r\n" + " \"name\": \"Carrot\"\r\n" + "}\r\n" + ",\r\n"
				+ "{\r\n" + " \"name\": \"Tomato\"\r\n" + "}\r\n" + ",\r\n" + "{\r\n" + " \"name\": \"Water\"\r\n"
				+ "}\r\n" + "  ],\r\n" + "  \"id\": 0,\r\n" + "  \"veg\": false\r\n" + "}";
		mockMvc.perform(post(API_V1_DISH).content(content)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
				.andExpect(status().isOk());

	}
	/**
	 * Test to check if receipe id is deleted from controller
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	
	@Test
	@WithMockUser(username = "dummy", roles = "user")
    void deleteRecipeByIdSuccess() throws Exception {
        mockMvc.perform(delete(API_V1_DISH + dishList.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

	/**
	 * Test to check if exception is thrown while delete from controller
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	@Test
	@WithMockUser(username = "dummy", roles = "user")
	void testDeleteRecipeByIdFail() throws Exception {
		long randomId = (long) (Math.random() * 100);
		mockMvc.perform(delete(API_V1_DISH +"/"+ randomId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(
						result -> assertThat(result.getResolvedException()).isInstanceOf(RecipeNotFoundException.class))
				.andExpect(result -> assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage())
						.isEqualTo("receipe ID not found :" + randomId ));
	}
	/**
	 * Test to search receipe from controller
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	@Test
	@WithMockUser(username = "dummy", roles = "user")
	void testGetRecipe() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/dish/searchrecipe?createdDate=01‐01‐2022 09:00&isVeg=false&suitableFor=1&ingredients=Carrot&instructions=add")
		
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

}
