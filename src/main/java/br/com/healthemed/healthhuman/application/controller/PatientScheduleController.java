package br.com.healthemed.healthhuman.application.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.healthemed.healthhuman.application.dto.CheckoutScheduleRequest;
import br.com.healthemed.healthhuman.application.dto.ScheduleDto;
import br.com.healthemed.healthhuman.application.dto.ScheduleMapper;
import br.com.healthemed.healthhuman.domain.usecase.IMedicalScheduleUseCase;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class PatientScheduleController {

	private final ScheduleMapper scheduleMapper;

	private final IMedicalScheduleUseCase medicalScheduleUseCase;

	@PostMapping("/checkout")
	public ScheduleDto checkout(@RequestBody CheckoutScheduleRequest request) {
		return Optional.ofNullable(medicalScheduleUseCase.checkout(request.getPatientId(), request))
				.map(scheduleMapper::toScheduleDto)
				// FIXME: exceção própria
				.orElseThrow(() -> new RuntimeException("Probleminha no checkout!"));
	}
}
