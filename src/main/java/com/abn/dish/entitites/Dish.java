package com.abn.dish.entitites;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="dish")
@Getter
@Setter
public class Dish {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	private String nameOfDish;
	@Column(name="creationDateTime")
	private LocalDateTime creationDateTime;
	private int suitableFor;
	private boolean isVeg;
	private String instructions;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="dish_Id")
	private Set<Ingredients> ingredients;
	

}