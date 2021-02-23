package com.mpHeroesCompany.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
	@JoinTable(name = "user_dailyPracticeState", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "dailyPracticeState_id"))
	private DailyPracticeState dailyPracticeState;
}
