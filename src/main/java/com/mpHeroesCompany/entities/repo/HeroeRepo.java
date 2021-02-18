package com.mpHeroesCompany.entities.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpHeroesCompany.entities.Heroe;

public interface HeroeRepo extends JpaRepository<Heroe, Integer> {
	List<Heroe> findByCurrentPowerLessThanEqual(int CurrentPower);

	List<Heroe> findByCurrentPowerGreaterThanEqual(int CurrentPower);

//	List<Heroe> findByCurrentPowerBetween();
}
