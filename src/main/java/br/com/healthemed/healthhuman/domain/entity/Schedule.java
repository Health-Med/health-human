package br.com.healthemed.healthhuman.domain.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {

	private Long id;
	
	private String doctorId;
	
	private LocalDateTime dateTime;
}
