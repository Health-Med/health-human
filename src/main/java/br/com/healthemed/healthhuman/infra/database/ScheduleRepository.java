package br.com.healthemed.healthhuman.infra.database;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.healthemed.healthhuman.infra.database.entity.ScheduleEntity;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
	
	List<ScheduleEntity> findAllByDoctorId(String doctorId);
	
	@Query(value = "SELECT * FROM TB_SCHEDULE WHERE PATIENT_ID = ?1", nativeQuery = true)
	List<ScheduleEntity> findAllByPatientId(String patientId);
	
	@Query(value = "SELECT * FROM TB_SCHEDULE WHERE DOCTOR_ID = ?1 AND DATE(SCHEDULE_DATE) = ?2", nativeQuery = true)
	List<ScheduleEntity> findAllByDoctorIdAndDate(String doctorId, LocalDate date);
	
	@Query(value = "SELECT * FROM TB_SCHEDULE WHERE DOCTOR_ID = ?1 AND SCHEDULE_DATE = ?2", nativeQuery = true)
	ScheduleEntity findByDoctorIdAndDate(String doctorId, LocalDateTime dateTime);
	
	@Query(value = "SELECT * FROM TB_SCHEDULE WHERE DOCTOR_ID = ?1 AND SCHEDULE_DATE BETWEEN ?2 AND ?3", nativeQuery = true)
	ScheduleEntity findByDoctorIdAndDateBetween(String doctorId, LocalDateTime start, LocalDateTime end);
	
	@Query(value = "SELECT * FROM TB_SCHEDULE WHERE DOCTOR_ID = ?1 AND YEAR(SCHEDULE_DATE) = ?2 AND MONTH(SCHEDULE_DATE) = ?3", nativeQuery = true)
	List<ScheduleEntity> findAllByDoctorIdAndYearAndMonth(String doctorId, Integer year, Integer month);
	
	@Modifying
	@Transactional
	@Query(value = "delete from ScheduleEntity o where o.ID = ?1")
	void deleteById(Long id);
}
