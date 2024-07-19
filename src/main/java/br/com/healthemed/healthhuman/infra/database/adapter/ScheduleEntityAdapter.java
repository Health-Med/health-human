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
	public ScheduleEntity getByDoctorAndDateTime(String doctorId, LocalDateTime dateTime) {
		return repository.findByDoctorIdAndDate(doctorId, dateTime.withMinute(00).withSecond(00).withNano(0)); // FIXME: corrigir uso de minuto
	}
	
	@Override
	public ScheduleEntity getByDoctorAndDateTimeBetween(String doctorId, LocalDateTime start, LocalDateTime end) {
		return repository.findByDoctorIdAndDateBetween(doctorId, start, end);
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
	public ScheduleEntity save(String doctorId, LocalDateTime dateTime, ScheduleStatus status) {
		return save(new ScheduleEntity(doctorId, dateTime, status));
	}

	@Override
	public ScheduleEntity save(ScheduleEntity entity) {
		return repository.save(entity);
	}
}
