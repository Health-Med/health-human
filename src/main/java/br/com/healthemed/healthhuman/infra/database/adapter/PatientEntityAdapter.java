package br.com.healthemed.healthhuman.infra.database.adapter;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.healthemed.healthhuman.application.dto.CreatePatientRequest;
import br.com.healthemed.healthhuman.domain.entity.Cpf;
import br.com.healthemed.healthhuman.domain.entity.Email;
import br.com.healthemed.healthhuman.domain.exception.UserAlreadyExistantException;
import br.com.healthemed.healthhuman.domain.repository.IPatientEntityAdapter;
import br.com.healthemed.healthhuman.infra.database.PatientRepository;
import br.com.healthemed.healthhuman.infra.database.entity.PatientEntity;
import br.com.healthemed.healthhuman.infra.database.entity.UserType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class PatientEntityAdapter implements IPatientEntityAdapter {
	
	private final PatientRepository repository;

	@Override
	@Cacheable(value = "all_doctors", unless = "#result.getTotalElements()==0")
	public Page<PatientEntity> findAll(int page, int size) {
		return repository.findAll(PageRequest.of(page, size));
	}
	
	@Override
	public Optional<PatientEntity> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public PatientEntity create(CreatePatientRequest request) {
		var patientBuilder = PatientEntity.builder().type(UserType.PATIENT).name(request.getName());
		
		if(StringUtils.isNotEmpty(request.getCpf())) {
			var cpf = new Cpf(request.getCpf());
			findByCpf(cpf).map(user -> user.getCpf()).ifPresent(e -> {
				throw new UserAlreadyExistantException(e);
			});
			patientBuilder = patientBuilder.cpf(cpf.getValue());
		}
		
		if(StringUtils.isNotEmpty(request.getEmail())) {
			var email = new Email(request.getEmail());
			findByEmail(email).map(user -> user.getEmail()).ifPresent(e -> {
				throw new UserAlreadyExistantException(e);
			});
			patientBuilder = patientBuilder.email(email.getValue());
		}
		
		return repository.save(patientBuilder.build());
	}

	@Override
	public Optional<PatientEntity> findByCpf(Cpf cpf) {
		return repository.findByCpf(cpf.getValue());
	}
	
	@Override
	public Optional<PatientEntity> findByEmail(Email email) {
		return repository.findByEmail(email.getValue());
	}
}
