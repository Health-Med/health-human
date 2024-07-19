package br.com.healthemed.healthhuman.application.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckoutScheduleRequest implements Serializable {
	
	private static final long serialVersionUID = -621830335594903665L;
	
	private Long scheduleId;
	
	private Long patientId;
}
