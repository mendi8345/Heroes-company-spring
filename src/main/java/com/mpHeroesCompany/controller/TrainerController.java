package com.mpHeroesCompany.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpHeroesCompany.entities.DailyPracticeState;
//import com.mpHeroesCompany.entities.DailyPracticeState;
import com.mpHeroesCompany.entities.Heroe;
import com.mpHeroesCompany.entities.Trainer;
import com.mpHeroesCompany.entities.repo.DailyStateRepo;
import com.mpHeroesCompany.entities.repo.HeroeRepo;
import com.mpHeroesCompany.entities.repo.TrainerRepo;
import com.mpHeroesCompany.service.TrainerService;
import com.mpHeroesCompany.springSecurity.payload.response.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/trainer")
public class TrainerController {
	@Autowired
	private HeroeRepo heroeRepo;

	@Autowired
	private TrainerRepo trainerRepo;
	@Autowired
	private TrainerService trainerService;

	@Autowired
	private DailyStateRepo dailyStateRepo;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addHeroe/{trainerId}")
	public ResponseEntity<?> AddHeroe(@PathVariable int trainerId, @RequestBody Heroe heroeRequest) {
		System.out.println(trainerId);
		if (trainerRepo.existsById(trainerId)) {
			System.out.println(heroeRequest);
			DailyPracticeState day = new DailyPracticeState();
			day.setDay(trainerService.getCurrentDate());
			day.setPracticeCounter(0);
			dailyStateRepo.save(day);
			heroeRequest.setDailyPracticeState(day);
			heroeRepo.save(heroeRequest);

			Trainer trainer = trainerRepo.findById(trainerId).get();
			trainer.getHeroes().add(heroeRequest);
			trainerRepo.save(trainer);
			return ResponseEntity.ok(new MessageResponse("heroe is successfully added!"));

		} else {

			return ResponseEntity.badRequest().body(new MessageResponse("Error: no heroes taken!"));
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/updateHeroe/{trainerId}/{heroeId}")
	public ResponseEntity<?> update(@PathVariable int trainerId, @PathVariable int heroeId,
			@RequestBody Heroe heroeRequest) {
		System.out.println(trainerId);
		if (trainerRepo.existsById(trainerId)) {
			System.out.println("heroeRequest = " + heroeRequest);
			if (heroeRepo.existsById(heroeId)) {
				heroeRequest.setId(heroeId);
				System.out.println("heroeRequest = " + heroeRequest);
				heroeRepo.save(heroeRequest);
				return ResponseEntity.ok(new MessageResponse("heroe successfully updated!"));
			} else {
				return ResponseEntity.badRequest()
						.body(new MessageResponse("Error:  hero don't exist with id: " + heroeId + "!"));
			}

		} else {

			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: no trainer exist with id: " + trainerId + "!"));
		}
	}

	@CrossOrigin(origins = "*")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/train/{trainerId}/{heroeId}")
	public ResponseEntity<?> trainHeroe(@PathVariable int trainerId, @PathVariable int heroeId) {
		if (trainerRepo.existsById(trainerId)) {
			if (heroeRepo.existsById(heroeId)) {

				Heroe heroe = heroeRepo.findById(heroeId).get();
				if (trainerService.isAllowToTrain(heroe.getDailyPracticeState())) {
					Random rand = new Random();
					int random = rand.nextInt(10);
					heroe.setCurrentPower(heroe.getCurrentPower() / 100 * random + heroe.getCurrentPower());
					heroeRepo.save(heroe);
					return ResponseEntity.ok(heroe);
				}
				return ResponseEntity.badRequest()
						.body(new MessageResponse("Error: hero can train just-5 times in one day!"));
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: no heroes taken!"));
			}
		} else {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: no trainer exist with id: " + trainerId + "!"));
		}
	}

	private Date getCurrentDate() {
		int year = new Date().getYear();
		int month = new Date().getMonth();
		int date = new Date().getDate();
		return new Date(year, month, date);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerTrainer(@Valid @RequestBody Trainer trainerRequest) {
		System.out.println("inside signup trainer!!!!!!!!!");
		if (trainerRepo.existsById(trainerRequest.getId())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: heroe is already exist!"));
		} else {
			trainerRepo.save(trainerRequest);
			return ResponseEntity.ok(new MessageResponse("Trainer registered successfully!"));
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/all/{trainerId}")
	@CrossOrigin(origins = "*")
	public ResponseEntity<?> getAllHeroe(@PathVariable int trainerId) {
		try {
			if (!trainerRepo.existsById(trainerId)) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: trainer dos not exist!"));
			}
			List<Heroe> heroes = trainerRepo.findById(trainerId).get().getHeroes();
			return ResponseEntity.ok(heroes);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: no Heroes taken"));
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/allByPower/{power}")
	public ResponseEntity<?> getAllHeroeByPower(@PathVariable int power) {
		try {
			List<Heroe> heroes = heroeRepo.findByCurrentPowerGreaterThanEqual(power);
			return ResponseEntity.ok(heroes);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: no Heroes taken"));
		}
	}

}