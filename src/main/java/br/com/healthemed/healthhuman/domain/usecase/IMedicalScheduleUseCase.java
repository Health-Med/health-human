package br.com.healthemed.healthhuman.domain.usecase;

import java.util.UUID;

import br.com.healthemed.healthhuman.application.dto.AllowOrRejectDoctorScheduleRequest;
import br.com.healthemed.healthhuman.application.dto.CheckoutScheduleRequest;
import br.com.healthemed.healthhuman.application.dto.OpenDoctorScheduleRequest;
import br.com.healthemed.healthhuman.application.dto.UpdateDoctorScheduleRequest;
import br.com.healthemed.healthhuman.infra.database.entity.ScheduleEntity;

public interface IMedicalScheduleUseCase {
	
	ScheduleEntity openDoctorSchedule(String doctorId, OpenDoctorScheduleRequest request);

	ScheduleEntity updateDoctorSchedule(String doctorId, UpdateDoctorScheduleRequest request);
	
	ScheduleEntity checkout(UUID patientId, CheckoutScheduleRequest request);
	
	ScheduleEntity updatePatientSchedule(String patientId, AllowOrRejectDoctorScheduleRequest request);
}
