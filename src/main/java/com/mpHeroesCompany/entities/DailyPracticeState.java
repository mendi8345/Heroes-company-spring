package com.mpHeroesCompany.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DailyPracticeState")
public class DailyPracticeState {
	@Id
	@GeneratedValue
	private int id;
	private Date day;
	private int practiceCounter;

}
