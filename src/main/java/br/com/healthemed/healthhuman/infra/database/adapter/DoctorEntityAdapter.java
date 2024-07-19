package br.com.healthemed.healthhuman.infra.database.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.healthemed.healthhuman.domain.repository.IDoctorEntityAdapter;
import br.com.healthemed.healthhuman.infra.database.DoctorRepository;
import br.com.healthemed.healthhuman.infra.database.entity.DoctorEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class DoctorEntityAdapter implements IDoctorEntityAdapter {
	
	private final DoctorRepository repository;

	@Override
	public Page<DoctorEntity> findAll(int page, int size) {
		return repository.findAll(PageRequest.of(page, size));
	}

	@Override
	public DoctorEntity create(DoctorEntity newDoctor) {
		return repository.save(newDoctor);
	}

	@Override
	public Page<DoctorEntity> findAllBySpeciality(int page, int size, String speciality) {
		return repository.findAllBySpecialityContainingIgnoreCase(speciality, PageRequest.of(page, size));
	}

	@Override
	public Page<DoctorEntity> findAllByDistance(int page, int size, Long latTarget, Long longTarget) {
		// TODO: persistir latitude e longitude de cada endereço de doutores
		// TODO: calcular distancia confirme a latitude e longitude
		return Page.empty();
	}

	@Override
	public Page<DoctorEntity> findAllByRating(int page, int size, Integer rating) {
		return repository.findAllByRating(rating, PageRequest.of(page, size));
	}

}
