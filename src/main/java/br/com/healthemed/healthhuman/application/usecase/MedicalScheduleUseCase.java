package br.com.healthemed.healthhuman.application.usecase;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.healthemed.healthhuman.application.dto.AllowOrRejectDoctorScheduleRequest;
import br.com.healthemed.healthhuman.application.dto.CheckoutScheduleRequest;
import br.com.healthemed.healthhuman.application.dto.OpenDoctorScheduleRequest;
import br.com.healthemed.healthhuman.application.dto.UpdateDoctorScheduleRequest;
import br.com.healthemed.healthhuman.domain.entity.ScheduleStatus;
import br.com.healthemed.healthhuman.domain.exception.ScheduleException;
import br.com.healthemed.healthhuman.domain.exception.ScheduleNotFoundException;
import br.com.healthemed.healthhuman.domain.exception.StatusNotFoundException;
import br.com.healthemed.healthhuman.domain.exception.UserNotFoundException;
import br.com.healthemed.healthhuman.domain.usecase.IMedicalScheduleUseCase;
import br.com.healthemed.healthhuman.infra.database.DoctorRepository;
import br.com.healthemed.healthhuman.infra.database.ScheduleRepository;
import br.com.healthemed.healthhuman.infra.database.adapter.ScheduleEntityAdapter;
import br.com.healthemed.healthhuman.infra.database.entity.ScheduleEntity;
import br.com.healthemed.healthhuman.infra.database.entity.UserType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicalScheduleUseCase implements IMedicalScheduleUseCase {
	
	@Value("${max.query.duration:50}")
	private Long maxQueryDuration;

	private final ScheduleEntityAdapter scheduleAdapter;
	
	private final ScheduleRepository scheduleRepository;
	
	private final DoctorRepository userRepository;

	@Override
	public ScheduleEntity openDoctorSchedule(String doctorId, OpenDoctorScheduleRequest request) {
		
		var doctor = userRepository.findById(doctorId).orElseThrow(UserNotFoundException::new);
		
		var begin = request.getDateTime();
		
		var endDateTime = begin.plusMinutes(Duration.ofMinutes(this.maxQueryDuration).toMinutes()).minusSeconds(1L);
		if (!scheduleAdapter.getByDoctorAndDateTimeBetween(doctor.getId(), begin, endDateTime).isEmpty()) {
			throw new ScheduleException("A agenda já se encontra fechada para esta data/hora.");
		}

		return scheduleAdapter.save(doctorId, begin, endDateTime, ScheduleStatus.OPENED, UserType.DOCTOR);
	}

	/**
	 * Dr confirma/rejeita agendamento do paciente
	 */
	@Override
	public ScheduleEntity updateDoctorSchedule(String doctorId, UpdateDoctorScheduleRequest request) {
		var doctor = userRepository.findById(doctorId).orElseThrow(UserNotFoundException::new);
		
		var schedule = scheduleAdapter.getById(request.getId())
				.orElseThrow(() -> new ScheduleNotFoundException("Agenda não encontrada"));
		if (!schedule.getDoctorId().equalsIgnoreCase(doctor.getId())) {
			throw new ScheduleNotFoundException("Alteração não permitida");
		}

		Optional.ofNullable(request.getDateTime()).ifPresent(dt -> schedule.setSchedule(dt));
			
		switch (schedule.getStatus()) { // ESTADO ATUAL DA AGENDA
		case SCHEDULED: // paciente escolheu agenda para consulta
			switch (request.getStatus()) {
			case ACCEPTED: // e médico está aceitando a consulta
				schedule.setStatus(request.getStatus());
				schedule.setJustification(null);
				break;
			case REJECTED: // e médico está rejeitando a consulta
				schedule.setStatus(request.getStatus());
				schedule.setJustification(Optional.ofNullable(request.getJustification())
						.orElseThrow(() -> new ScheduleNotFoundException("Justificativa necessária para rejeitar consulta")));
				break;
			default:
				throw new ScheduleNotFoundException("Outros status são desconhedidos na atualização da consulta.");
			}
			break;
		case ACCEPTED: // médico aceitou consulta do paciente
			switch (request.getStatus()) {
			case ACCEPTED:
				throw new ScheduleNotFoundException("Consulta já foi aceita previamente");
			case REJECTED:
				schedule.setStatus(request.getStatus());
				schedule.setJustification(Optional.ofNullable(request.getJustification())
						.orElseThrow(() -> new ScheduleNotFoundException("Justificativa necessária para rejeitar consulta")));
				schedule.setStatusBy(UserType.DOCTOR);
				break;
			default:
				throw new ScheduleNotFoundException("Outros status são desconhedidos ao atualizar consulta aceita.");
			}
			break;
		case REJECTED: // médico rejeitou consulta do paciente
			switch (request.getStatus()) {
			case REJECTED:
				throw new ScheduleNotFoundException("Consulta já foi rejeitada previamente.");
			case CANCELED:
				throw new ScheduleNotFoundException("Consulta cancelada não pode ser rejeitada.");
			case OPENED: // e agenda está sendo reaberta
				schedule.setStatus(request.getStatus());
				schedule.setJustification(null);
				schedule.setStatusBy(UserType.DOCTOR);
				break;
			case ACCEPTED:
				// Cliente precisa setar uma consulta para ser aceita
				schedule.setStatus(request.getStatus());
				schedule.setJustification(null);
				schedule.setStatusBy(UserType.DOCTOR);
				break;
			default:
				throw new ScheduleNotFoundException("Outros status são desconhedidos ao atualizar consulta rejeitada.");
			}
			break;
		default:
			throw new StatusNotFoundException();
		}

		return scheduleAdapter.save(schedule);
	}

	@Override
	public ScheduleEntity checkout(UUID patientId, CheckoutScheduleRequest request) {
		var schedule = scheduleAdapter.getById(request.getScheduleId())
				.orElseThrow(() -> new ScheduleNotFoundException("Agenda não encontrada"));
		
		if (!schedule.getStatus().equals(ScheduleStatus.OPENED)) {
			throw new ScheduleException("impossível confirmar agenda que não está aberta.");
		}
		
		schedule.setStatus(ScheduleStatus.SCHEDULED);
		schedule.setJustification(null);
		schedule.setPatientId(patientId.toString());
		return scheduleAdapter.save(schedule);
	}
	
	/**
	 * Paciente rejeita a consulta
	 */
	@Override
	public ScheduleEntity updatePatientSchedule(String patientId, AllowOrRejectDoctorScheduleRequest request) {
		var schedule = scheduleRepository.findAllByPatientId(patientId).stream().findFirst()
			.orElseThrow(ScheduleNotFoundException::new);
		
		if (ScheduleStatus.REJECTED.equals(request.getStatus())) {
			schedule.setStatus(request.getStatus());
			schedule.setJustification(Optional.ofNullable(request.getJustification())
					.orElseThrow(() -> new ScheduleException("Justificativa necessária para rejeitar uma agenda")));
			schedule.setStatusBy(UserType.PATIENT);
			return scheduleRepository.save(schedule);
		} 
		throw new StatusNotFoundException();
	}

}
