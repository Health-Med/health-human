package br.com.healthemed.healthhuman.infra.database;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.healthemed.healthhuman.infra.database.entity.PatientEntity;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, String>{

	Page<PatientEntity> findAll(Pageable pageable);
	
	Optional<PatientEntity> findByEmail(String email);
	
	Optional<PatientEntity> findByCpf(String cpf);
}
