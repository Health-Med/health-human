package br.com.healthemed.healthhuman.application.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.healthemed.healthhuman.application.dto.OpenDoctorScheduleRequest;
import br.com.healthemed.healthhuman.application.dto.ScheduleDto;
import br.com.healthemed.healthhuman.application.dto.ScheduleMapper;
import br.com.healthemed.healthhuman.application.dto.UpdateDoctorScheduleRequest;
import br.com.healthemed.healthhuman.domain.exception.ScheduleNotFoundException;
import br.com.healthemed.healthhuman.domain.repository.IScheduleEntityAdapter;
import br.com.healthemed.healthhuman.domain.usecase.IMedicalScheduleUseCase;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
	
	private final ScheduleMapper scheduleMapper;
	
	private final IMedicalScheduleUseCase medicalScheduleUseCase;
	
	private final IScheduleEntityAdapter scheduleEntityAdapter;
	
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Doctor's schedule for year and month", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(allOf = ScheduleDto.class)) })
	})
	@GetMapping("/{doctorId}/{year}/{month}")
	public List<ScheduleDto> getScheduleByMonth(@PathVariable String doctorId, @PathVariable Integer year, @PathVariable Integer month) {
		return scheduleEntityAdapter.getAllByDoctorAndYearAndMonth(doctorId, year, month).stream()
			.map(scheduleMapper::toScheduleDto)
			.toList();
	}
	
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Doctor's schedule for year, month and day", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(allOf = ScheduleDto.class)) })
	})
	@GetMapping("/{doctorId}/{year}/{month}/{day}")
	public List<ScheduleDto> getScheduleByMonthAndDay(@PathVariable String doctorId, @PathVariable Integer year, @PathVariable Integer month, @PathVariable Integer day) {
		return scheduleEntityAdapter.getAllByDoctorAndYearAndMonthAndDay(doctorId, year, month, day).stream()
				.map(scheduleMapper::toScheduleDto)
				.toList();
	}
	
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Doctor's schedule for year, month, day and hour", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(allOf = ScheduleDto.class)) })
	})
	@GetMapping("/{doctorId}/{year}/{month}/{day}/{hour}")
	public ScheduleDto getScheduleByMonthAndDayAndHour(@PathVariable String doctorId, @PathVariable Integer year, @PathVariable Integer month, @PathVariable Integer day, @PathVariable Integer hour) {
		var start = LocalDateTime.now()
				.withYear(year)
				.withMonth(month)
				.withDayOfMonth(day)
				.withHour(hour)
				.withMinute(00)
				.withSecond(00)
				.withNano(0);
		var end = start
				.withMinute(59);
		return Optional.ofNullable(scheduleEntityAdapter.getByDoctorAndDateTimeBetween(doctorId, start, end))
				.map(scheduleMapper::toScheduleDto)
				.orElseThrow(ScheduleNotFoundException::new);
	}
	
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Open doctor schedule, on datetime", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(allOf = ScheduleDto.class)) })
	})
	@PostMapping("/{doctorId}")
	public ScheduleDto openDoctorSchedule(@PathVariable String doctorId, @Valid @RequestBody OpenDoctorScheduleRequest request) {
		var schedule = medicalScheduleUseCase.openDoctorSchedule(doctorId, request);
		return scheduleMapper.toScheduleDto(schedule);
	}
	
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Update status of doctor schedule", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(allOf = ScheduleDto.class)) })
	})
	@PutMapping("/{doctorId}")
	public ScheduleDto updateSchedule(@PathVariable String doctorId, @Valid @RequestBody UpdateDoctorScheduleRequest request) {
		return Optional.ofNullable(medicalScheduleUseCase.updateDoctorSchedule(doctorId, request))
				.map(scheduleMapper::toScheduleDto)
				.orElseThrow(ScheduleNotFoundException::new);
	}
}
