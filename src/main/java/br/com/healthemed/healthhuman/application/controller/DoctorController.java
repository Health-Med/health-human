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
import br.com.healthemed.healthhuman.application.dto.ResponseQueryPage;
import br.com.healthemed.healthhuman.domain.repository.IDoctorEntityAdapter;
import br.com.healthemed.healthhuman.infra.database.entity.DoctorEntity;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/query/doctors")
@RequiredArgsConstructor
public class DoctorController {

	private static final double EARTH_RADIUS = 6371;

	private final DoctorMapper mapper;

	private final IDoctorEntityAdapter doctorAdapter;

	@GetMapping
	public ResponseQueryPage getAll(
			@RequestParam(required = true, defaultValue = "0") Integer page,
			@RequestParam(required = true, defaultValue = "10") Integer size,
			@RequestParam(required = false) String speciality,
			@RequestParam(required = false) Integer rating,
			@RequestParam(required = false) Integer maxDistance,
			@RequestParam(required = false) Double latitude,
			@RequestParam(required = false) Double longitude) {
		
		if (speciality != null) {
			var pageFound = doctorAdapter.findAllBySpeciality(page, size, speciality);
					
			List<DoctorDto> lista = pageFound.stream().map(mapper::toDto).toList();
			return new ResponseQueryPage(lista, page, pageFound.getTotalPages(), pageFound.getTotalElements());
		}
		
		if (rating != null) {
			var pageFound = doctorAdapter.findAllByRating(page, size, rating);
			return new ResponseQueryPage(pageFound.map(mapper::toDto).toList(), page, 
					pageFound.getTotalPages(), pageFound.getTotalElements());
		}
		
		if (maxDistance != null && latitude != null && longitude != null) {
			var pageFound = doctorAdapter.findAll(page, size);
			var lista = pageFound.stream()
					.map(entity -> {
						var distanceInKm = calculateDistanceInKm(latitude, longitude, entity.getLatitude(), entity.getLongitude());
						var dto = mapper.toDto(entity);
						dto.setDistanceInKm(roundFor2Cases(distanceInKm));
						return dto;
					})
					.filter(dto -> dto.getDistanceInKm() <= maxDistance)
					.sorted((d1, d2) -> d1.getDistanceInKm().compareTo(d2.getDistanceInKm()))
					.toList();
			// FIXME: tentar corrigir quantidade total de páginas
			return new ResponseQueryPage(lista, page, page, lista.size());
		}
		
		var pageFound = doctorAdapter.findAll(page, size);
		return new ResponseQueryPage(pageFound.map(mapper::toDto).toList(), page, 
				pageFound.getTotalPages(), pageFound.getTotalElements());
	}
	
	private Double roundFor2Cases(Double distance) {
		return Math.floor(distance * 100) / 100;
	}
	
	double haversine(double val) {
	    return Math.pow(Math.sin(val / 2), 2);
	}
	
	double calculateDistanceInKm(double startLat, double startLong, double endLat, double endLong) {
	    double dLat = Math.toRadians((endLat - startLat));
	    double dLong = Math.toRadians((endLong - startLong));

	    startLat = Math.toRadians(startLat);
	    endLat = Math.toRadians(endLat);

	    double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	    return EARTH_RADIUS * c;
	}

	@PostMapping
	public DoctorDto create(@RequestBody CreateDoctorRequest request) {
		var newDoctor = new DoctorEntity(request.getName(), request.getSpeciality(), request.getZipCode(),
				request.getAddress(), request.getNumber(), request.getComplement(), request.getCity(), request.getState(), request.getCountry(), request.getRating());
		return Optional.ofNullable(doctorAdapter.create(newDoctor))
				.map(mapper::toDto)
				.orElseThrow(() -> new RuntimeException("Ocorreu uma falha xpto"));
	}
}
