package br.com.healthemed.healthhuman.application.dto;

import org.springframework.stereotype.Component;

import br.com.healthemed.healthhuman.infra.database.entity.DoctorEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DoctorMapper {

	public DoctorDto toDto(DoctorEntity entity) {
		return new DoctorDto(entity.getId(), entity.getName(), entity.getSpeciality(), entity.getZipCode(),
				entity.getFullAddress(), entity.getComplement(), entity.getRating(), entity.getLatitude(),
				entity.getLongitude(), null);
	}
}
