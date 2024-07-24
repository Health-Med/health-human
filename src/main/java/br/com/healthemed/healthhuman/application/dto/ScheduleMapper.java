package br.com.healthemed.healthhuman.application.dto;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.healthemed.healthhuman.domain.entity.Schedule;
import br.com.healthemed.healthhuman.infra.database.entity.ScheduleEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScheduleMapper {
	
	public Schedule toSchedule(ScheduleEntity entity) {
		return new Schedule(entity.getId(), entity.getDoctorId(), entity.getSchedule());
	}

	public ScheduleDto toScheduleDto(ScheduleEntity schedule) {
		var patientId = Optional.ofNullable(schedule.getPatientId()).map(p -> UUID.fromString(p)).orElse(null);
		return new ScheduleDto(schedule.getId(), schedule.getDoctorId(), schedule.getSchedule(), schedule.getScheduleEnd(), 
				schedule.getCreatedTime(), schedule.getUpdatedTime(), schedule.getStatus(), schedule.getStatusBy(), schedule.getJustification(), patientId);
	}
	
	public ScheduleEntity toScheduleEntity(ScheduleDto dto) {
		return new ScheduleEntity(dto.getId(), dto.getDoctorId(), dto.getScheduleStart(), dto.getScheduleEnd(), 
				dto.getCreationDateTime(), dto.getUpdateDateTime(), dto.getStatus(), dto.getStatusBy(), dto.getJustification(), dto.getPatientId().toString());
	}
}
