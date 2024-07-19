package br.com.healthemed.healthhuman.application.dto;

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
		return new ScheduleDto(schedule.getId(), schedule.getDoctorId(), schedule.getCreatedTime(), schedule.getUpdatedTime(), schedule.getSchedule(), schedule.getStatus(), schedule.getJustification(), schedule.getPatientId());
	}
	
	public ScheduleEntity toScheduleEntity(ScheduleDto dto) {
		return new ScheduleEntity(dto.getId(), dto.getDoctorId(), dto.getCreationDateTime(), dto.getUpdateDateTime(), dto.getSchedule(), dto.getStatus(), dto.getJustification(), dto.getPatientId());
	}
}
