package br.com.healthemed.healthhuman.infra.database.adapter;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.healthemed.healthhuman.application.dto.CreateDoctorRequest;
import br.com.healthemed.healthhuman.domain.exception.LocationException;
import br.com.healthemed.healthhuman.domain.repository.IDoctorEntityAdapter;
import br.com.healthemed.healthhuman.infra.adapter.NominatimRestClient;
import br.com.healthemed.healthhuman.infra.database.DoctorRepository;
import br.com.healthemed.healthhuman.infra.database.entity.DoctorEntity;
import br.com.healthemed.healthhuman.infra.database.entity.UserType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class DoctorEntityAdapter implements IDoctorEntityAdapter {
	
	private final DoctorRepository repository;
	private final NominatimRestClient nominatimRestClient;

	@Override
	@Cacheable(value = "all_doctors", unless = "#result.getTotalElements()==0")
	public Page<DoctorEntity> findAll(int page, int size) {
		return repository.findAll(PageRequest.of(page, size));
	}

	@Override
	public DoctorEntity create(CreateDoctorRequest request) {
		var newDoctor = DoctorEntity.builder()
			.type(UserType.DOCTOR)
			.name(request.getName())
			.speciality(request.getSpeciality())
			.zipCode(request.getZipCode())
			.address(request.getAddress())
			.number(request.getNumber())
			.complement(request.getComplement())
			.city(request.getCity())
			.state(request.getState())
			.country(request.getCountry())
			.rating(request.getRating())
			.price(request.getPrice())
			.build();
		String addressQuery = newDoctor.getSearchableAddress();
		var location = nominatimRestClient.searchLocation(addressQuery).stream().findFirst()
				.orElseThrow(LocationException::new);
		newDoctor.setLatitude(location.getLat());
		newDoctor.setLongitude(location.getLon());
		return repository.save(newDoctor);
	}

	@Override
	@Cacheable(value = "all_doctors_with_speciality", unless = "#result.getTotalElements()==0")
	public Page<DoctorEntity> findAllBySpeciality(int page, int size, String speciality) {
		var param = "%" + speciality.toUpperCase() + "%";
		return repository.findBySpecialityContaining(param, PageRequest.of(page, size));
	}

	@Override
	@Cacheable(value = "all_doctors_with_rating", unless = "#result.getTotalElements()==0")
	public Page<DoctorEntity> findAllByRating(int page, int size, Integer rating) {
		return repository.findAllByRating(rating, PageRequest.of(page, size));
	}
}
