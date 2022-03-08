package com.abn.dish.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class DishDto {

	private Long Id;
	private String dishName;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd‐MM‐yyyy HH:mm")
	private LocalDateTime creationDateTime;
	private int suitableFor;
	private boolean isVeg;
	private String instructions;
	private Set<IngredientsDto> ingredients;

}
