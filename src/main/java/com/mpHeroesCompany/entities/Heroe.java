package com.mpHeroesCompany.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "heroe")
public class Heroe {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String ability;
	private String startDate;
	private int startingPower;
	private int currentPower;
	private String suitColors;
	@OneToOne
	private DailyPracticeState dailyPracticeState;
}
