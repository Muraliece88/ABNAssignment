
package com.abn.dish.entitites;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
public class Ingredients {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ingredientId;
	private String name;
	@ManyToOne(fetch = FetchType.LAZY)
	private Dish dish;

}
