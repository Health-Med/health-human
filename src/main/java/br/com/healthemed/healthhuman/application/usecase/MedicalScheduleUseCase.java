package br.com.healthemed.healthhuman.application.usecase;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.healthemed.healthhuman.application.dto.CheckoutScheduleRequest;
import br.com.healthemed.healthhuman.application.dto.OpenDoctorScheduleRequest;
import br.com.healthemed.healthhuman.application.dto.UpdateDoctorScheduleRequest;
import br.com.healthemed.healthhuman.domain.entity.ScheduleStatus;
import br.com.healthemed.healthhuman.domain.usecase.IMedicalScheduleUseCase;
import br.com.healthemed.healthhuman.infra.database.adapter.ScheduleEntityAdapter;
import br.com.healthemed.healthhuman.infra.database.entity.ScheduleEntity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicalScheduleUseCase implements IMedicalScheduleUseCase {

	private final ScheduleEntityAdapter medicalAdapter;

	@Override
	public ScheduleEntity openDoctorSchedule(String doctorId, OpenDoctorScheduleRequest request) {
		if (medicalAdapter.getByDoctorAndDateTime(doctorId, request.getDateTime()) != null) {
			// FIXME: criar exceção própria
			throw new RuntimeException("A agenda já se encontra aberta para esta data/hora.");
		}

		return medicalAdapter.save(doctorId, request.getDateTime(), ScheduleStatus.OPENED);
	}

	@Override
	public ScheduleEntity updateDoctorSchedule(String doctorId, UpdateDoctorScheduleRequest request) {
		var schedule = medicalAdapter.getById(request.getId())
				.orElseThrow(() -> new RuntimeException("Agenda não encontrada"));
		if (!schedule.getDoctorId().equalsIgnoreCase(doctorId)) {
			throw new RuntimeException("Alteração não permitida");
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
						.orElseThrow(() -> new RuntimeException("Justificativa necessária para rejeitar consulta")));
				break;
			// TODO: PatientScheduleUseCase deverá implementar cancelamento de consulta do paciente
			default:
				throw new RuntimeException("Outros status são desconhedidos na atualização da consulta.");
			}
			break;
		case ACCEPTED: // médico aceitou consulta do paciente
			switch (request.getStatus()) {
			case ACCEPTED:
				throw new RuntimeException("Consulta já foi aceita previamente");
			case REJECTED:
				schedule.setStatus(request.getStatus());
				schedule.setJustification(Optional.ofNullable(request.getJustification())
						.orElseThrow(() -> new RuntimeException("Justificativa necessária para rejeitar consulta")));
				break;
			default:
				throw new RuntimeException("Outros status são desconhedidos ao atualizar consulta aceita.");
			}
			break;
		case REJECTED: // médico rejeitou consulta do paciente
			switch (request.getStatus()) {
			case REJECTED:
				throw new RuntimeException("Consulta já foi rejeitada previamente.");
			case CANCELED:
				throw new RuntimeException("Consulta cancelada não pode ser rejeitada.");
			case OPENED: // e agenda está sendo reaberta
				schedule.setStatus(request.getStatus());
				schedule.setJustification(null);
				break;
			case ACCEPTED:
				// Cliente precisa setar uma consulta para ser aceita
				schedule.setStatus(request.getStatus());
				schedule.setJustification(null);
				break;
			default:
				throw new RuntimeException("Outros status são desconhedidos ao atualizar consulta rejeitada.");
			}
			break;
		default:
			throw new RuntimeException("Necessário um status conhecido");
		}

		return medicalAdapter.save(schedule);
	}

	@Override
	public ScheduleEntity checkout(Long patientId, CheckoutScheduleRequest request) {
		var schedule = medicalAdapter.getById(request.getScheduleId())
				.orElseThrow(() -> new RuntimeException("Agenda não encontrada"));
		
		if (!schedule.getStatus().equals(ScheduleStatus.OPENED)) {
			throw new RuntimeException("Só é possível confirmar uma agenda que esteja aberta.");
		}
		
		schedule.setStatus(ScheduleStatus.SCHEDULED);
		schedule.setJustification(null);
		schedule.setPatientId(request.getPatientId());
		return medicalAdapter.save(schedule);
	}

}
