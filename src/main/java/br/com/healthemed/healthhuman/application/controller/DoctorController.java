package br.com.healthemed.healthhuman.application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.healthemed.healthhuman.application.dto.CreateDoctorRequest;
import br.com.healthemed.healthhuman.application.dto.DoctorDto;
import br.com.healthemed.healthhuman.application.dto.DoctorMapper;
import br.com.healthemed.healthhuman.domain.repository.IDoctorEntityAdapter;
import br.com.healthemed.healthhuman.infra.database.entity.DoctorEntity;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/query/doctors")
@RequiredArgsConstructor
public class DoctorController {

	private final DoctorMapper mapper;

	private final IDoctorEntityAdapter doctorAdapter;

	@GetMapping
	public List<DoctorDto> getAll(
			@RequestParam(required = true, defaultValue = "0") Integer page, 
			@RequestParam(required = true, defaultValue = "10") Integer size,
			@RequestParam(required = false) String speciality,
			@RequestParam(required = false) Integer rating,
			@RequestParam(required = false) Integer distance) {
		
		if (speciality != null) {
			return doctorAdapter.findAllBySpeciality(page, size, speciality).map(mapper::toDto).toList();
		}
		
		if (rating != null) {
			return doctorAdapter.findAllByRating(page, size, rating).map(mapper::toDto).toList();
		}
		
		if (distance != null) {
			// FIXME: receber parãmetro base e calcular com base na distância
			return doctorAdapter.findAllByDistance(page, size, 123L, 123L).map(mapper::toDto).toList();
		}
		
		return doctorAdapter.findAll(page, size).map(mapper::toDto).toList();
	}

	@PostMapping
	public DoctorDto create(@RequestBody CreateDoctorRequest request) {
		var newDoctor = new DoctorEntity(request.getName(), request.getSpeciality(), request.getZipCode(),
				request.getAddress(), request.getNumber(), request.getComplement(), request.getRating());
		return Optional.ofNullable(doctorAdapter.create(newDoctor))
				.map(mapper::toDto)
				.orElseThrow(() -> new RuntimeException("Ocorreu uma falha xpto"));
	}
}
