package br.com.healthemed.healthhuman.application.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.healthemed.healthhuman.application.dto.AllowOrRejectDoctorScheduleRequest;
import br.com.healthemed.healthhuman.application.dto.CheckoutScheduleRequest;
import br.com.healthemed.healthhuman.application.dto.ScheduleDto;
import br.com.healthemed.healthhuman.application.dto.ScheduleMapper;
import br.com.healthemed.healthhuman.domain.entity.ScheduleStatus;
import br.com.healthemed.healthhuman.domain.exception.CheckoutException;
import br.com.healthemed.healthhuman.domain.exception.PatientScheduleException;
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
		var patientId = UUID.fromString(request.getPatientId());
		return Optional.ofNullable(medicalScheduleUseCase.checkout(patientId, request))
				.map(scheduleMapper::toScheduleDto)
				.orElseThrow(CheckoutException::new);
	}
	
	@PutMapping("/patient/{status}")
	public ScheduleDto update(@PathVariable ScheduleStatus status, @RequestBody AllowOrRejectDoctorScheduleRequest request) {
		return Optional.ofNullable(medicalScheduleUseCase.updatePatientSchedule(request.getPatientId(), request))
				.map(scheduleMapper::toScheduleDto)
				.orElseThrow(PatientScheduleException::new);
	}
}
