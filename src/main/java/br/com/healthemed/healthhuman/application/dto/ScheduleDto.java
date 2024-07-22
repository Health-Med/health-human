package br.com.healthemed.healthhuman.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.healthemed.healthhuman.domain.entity.ScheduleStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ScheduleDto {
	
	private static final String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd'T'hh:mm:ss";

	private Long id;
	
	private String doctorId;
	
	@JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN, shape = Shape.STRING)
	private LocalDateTime creationDateTime;
	
	@JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN, shape = Shape.STRING)
	private LocalDateTime updateDateTime;
	
	@JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN, shape = Shape.STRING)
	private LocalDateTime schedule;
	
	private ScheduleStatus status;
	
	private String justification;
	
	private UUID patientId;
}
