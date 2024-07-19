package br.com.healthemed.healthhuman.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchedulePatient {

	private Long id;
	
	private ScheduleStatus status;
	
	private String patientId;
	
	private String justification;
}
