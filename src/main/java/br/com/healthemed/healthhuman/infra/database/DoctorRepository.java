package br.com.healthemed.healthhuman.infra.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.healthemed.healthhuman.infra.database.entity.DoctorEntity;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, String>{

	Page<DoctorEntity> findAll(Pageable pageable);
	
	Page<DoctorEntity> findAllBySpecialityContainingIgnoreCase(String speciality, Pageable pageable);
	
	Page<DoctorEntity> findAllByRating(Integer rating, Pageable pageable);
}
