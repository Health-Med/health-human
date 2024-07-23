package br.com.healthemed.healthhuman.domain.repository;

import org.springframework.data.domain.Page;

import br.com.healthemed.healthhuman.application.dto.CreateDoctorRequest;
import br.com.healthemed.healthhuman.infra.database.entity.DoctorEntity;

public interface IDoctorEntityAdapter {

	Page<DoctorEntity> findAll(int page, int size);
	
	Page<DoctorEntity> findAllBySpeciality(int page, int size, String speciality);
	
	Page<DoctorEntity> findAllByRating(int page, int size, Integer rating);

	DoctorEntity create(CreateDoctorRequest request);
}
