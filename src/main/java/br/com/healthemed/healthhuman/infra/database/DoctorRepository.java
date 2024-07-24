package br.com.healthemed.healthhuman.infra.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.healthemed.healthhuman.infra.database.entity.DoctorEntity;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, String>{

	Page<DoctorEntity> findAll(Pageable pageable);
	
	@Query(value = "SELECT * FROM TB_USER WHERE USER_TYPE = 'DOCTOR' AND UPPER(SPECIALITY) LIKE ?1",
			countQuery = "SELECT count(*) FROM TB_USER WHERE UPPER(SPECIALITY) LIKE ?1",
			nativeQuery = true)
	Page<DoctorEntity> findBySpecialityContaining(String speciality, Pageable pageable);
	
	Page<DoctorEntity> findAllByRating(Integer rating, Pageable pageable);
}
