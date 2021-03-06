package com.mpHeroesCompany.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.mpHeroesCompany.entities.DailyPracticeState;

@Service
public class TrainerService {

	public boolean isAllowToTrain(DailyPracticeState dailyPracticeState) {
		System.out.println(" current dailyPracticeState = " + dailyPracticeState);
		System.out.println(dailyPracticeState.getDay().getDate() != getCurrentDate().getDate());

		if (dailyPracticeState.getDay().getDate() != getCurrentDate().getDate()) {
			dailyPracticeState.setPracticeCounter(5);
			dailyPracticeState.setDay(getCurrentDate());
		}

		if (dailyPracticeState.getPracticeCounter() > 0) {
			dailyPracticeState.setPracticeCounter(dailyPracticeState.getPracticeCounter() - 1);
			return true;
		}
		return false;
	}

	public Date getCurrentDate() {
		int year = new Date().getYear();
		int month = new Date().getMonth();
		int date = new Date().getDate();
		return new Date(year, month, date);
	}

}