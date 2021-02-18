package com.mpHeroesCompany.entities.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpHeroesCompany.entities.DailyPracticeState;

public interface DailyStateRepo extends JpaRepository<DailyPracticeState, Integer> {

}
