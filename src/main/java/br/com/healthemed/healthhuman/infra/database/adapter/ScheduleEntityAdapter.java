package br.com.healthemed.healthhuman.infra.database.adapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.healthemed.healthhuman.domain.entity.ScheduleStatus;
import br.com.healthemed.healthhuman.domain.repository.IScheduleEntityAdapter;
import br.com.healthemed.healthhuman.infra.database.ScheduleRepository;
import br.com.healthemed.healthhuman.infra.database.entity.ScheduleEntity;
import br.com.healthemed.healthhuman.infra.database.entity.UserType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class ScheduleEntityAdapter implements IScheduleEntityAdapter {
	private final ScheduleRepository repository;

	@Override
	public List<ScheduleEntity> getAll() {
		return repository.findAll();
	}
	
	@Override
	public List<ScheduleEntity> getAllByDoctorAndYearAndMonth(String doctorId, Integer year, Integer month) {
		return repository.findAllByDoctorIdAndYearAndMonth(doctorId, year, month);
	}
	
	@Override
	public List<ScheduleEntity> getAllByDoctorAndYearAndMonthAndDay(String doctorId, Integer year, Integer month, Integer day) {
		LocalDate date = LocalDate.now().withYear(year).withMonth(month).withDayOfMonth(day);
		return repository.findAllByDoctorIdAndDate(doctorId, date);
	}
	
	@Override
	public List<ScheduleEntity> getByDoctorAndDateTimeBetween(String doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		var start = startDateTime.withSecond(00).withNano(0);
		var end = endDateTime.withSecond(00).withNano(0);
		var agenda = repository.findByDoctorIdAndDateBetween(doctorId, start, end);
		return agenda;
	}

	@Override
	public Optional<ScheduleEntity> getById(Long id) {
		return repository.findById(id);
	}

	@Override
	public void deleteAllItemsByOrderId(String orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(String orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ScheduleEntity save(String doctorId, LocalDateTime startTime, LocalDateTime endTime, ScheduleStatus status, UserType statusBy) {
		return save(new ScheduleEntity(doctorId, startTime, endTime, status, statusBy));
	}

	@Override
	public ScheduleEntity save(ScheduleEntity entity) {
		return repository.save(entity);
	}
}
