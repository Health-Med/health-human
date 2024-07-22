package br.com.healthemed.healthhuman.application.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckoutScheduleRequest implements Serializable {
	
	private static final long serialVersionUID = -621830335594903665L;
	
	@NonNull
	@NotNull
	private Long scheduleId;
	
	@NonNull
	@NotNull
	private String patientId;
}
