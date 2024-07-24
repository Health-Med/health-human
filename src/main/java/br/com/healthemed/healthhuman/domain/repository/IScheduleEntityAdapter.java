package br.com.healthemed.healthhuman.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.com.healthemed.healthhuman.domain.entity.ScheduleStatus;
import br.com.healthemed.healthhuman.infra.database.entity.ScheduleEntity;

public interface IScheduleEntityAdapter {
	
	List<ScheduleEntity> getAll();
	
	List<ScheduleEntity> getAllByDoctorAndYearAndMonth(String doctorId, Integer year, Integer month);
	
	List<ScheduleEntity> getAllByDoctorAndYearAndMonthAndDay(String doctorId, Integer year, Integer month, Integer day);
	
	List<ScheduleEntity> getByDoctorAndDateTimeBetween(String doctorId, LocalDateTime start, LocalDateTime end);
	
	Optional<ScheduleEntity> getById(Long id);
	
	void deleteAllItemsByOrderId(String orderId);
	
	void deleteById(String orderId);
	
	public ScheduleEntity save(String doctorId, LocalDateTime startTime, LocalDateTime endTime, ScheduleStatus status);
	
	ScheduleEntity save(ScheduleEntity entity);
}
