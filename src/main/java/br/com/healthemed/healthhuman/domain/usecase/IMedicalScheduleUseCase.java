package br.com.healthemed.healthhuman.domain.usecase;

import br.com.healthemed.healthhuman.application.dto.OpenDoctorScheduleRequest;
import br.com.healthemed.healthhuman.application.dto.UpdateDoctorScheduleRequest;
import br.com.healthemed.healthhuman.infra.database.entity.ScheduleEntity;

public interface IMedicalScheduleUseCase {
	
	ScheduleEntity openDoctorSchedule(String doctorId, OpenDoctorScheduleRequest request);

	ScheduleEntity updateDoctorSchedule(String doctorId, UpdateDoctorScheduleRequest request);
}
