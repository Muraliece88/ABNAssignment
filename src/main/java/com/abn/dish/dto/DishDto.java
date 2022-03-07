package com.abn.dish.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.abn.dish.entitites.Ingredients;
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
