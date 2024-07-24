package br.com.healthemed.healthhuman.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.healthemed.healthhuman.application.dto.CreatePatientRequest;
import br.com.healthemed.healthhuman.domain.entity.Cpf;
import br.com.healthemed.healthhuman.domain.entity.Email;
import br.com.healthemed.healthhuman.infra.database.entity.PatientEntity;

public interface IPatientEntityAdapter {

	Page<PatientEntity> findAll(int page, int size);
	
	Optional<PatientEntity> findById(String id);
	
	Optional<PatientEntity> findByEmail(Email email);
	
	Optional<PatientEntity> findByCpf(Cpf cpf);

	PatientEntity create(CreatePatientRequest request);
}
