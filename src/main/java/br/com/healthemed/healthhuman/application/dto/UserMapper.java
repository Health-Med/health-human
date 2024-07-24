package br.com.healthemed.healthhuman.application.dto;

import org.springframework.stereotype.Component;

import br.com.healthemed.healthhuman.infra.database.entity.DoctorEntity;
import br.com.healthemed.healthhuman.infra.database.entity.PatientEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

	public DoctorDto toDto(DoctorEntity entity) {
		return DoctorDto.builder()
			.id(entity.getId())
			.name(entity.getName())
			.crm(entity.getCrm())
			.speciality(entity.getSpeciality())
			.zipCode(entity.getZipCode())
			.address(entity.getFullAddress())
			.complement(entity.getComplement())
			.rating(entity.getRating())
			.price(entity.getPrice())
			.latitude(entity.getLatitude())
			.longitude(entity.getLongitude())
			.build();
	}
	
	public PatientDto toDto(PatientEntity entity) {
		return PatientDto.builder()
				.id(entity.getId())
				.name(entity.getName())
				.email(entity.getEmail())
				.cpf(entity.getCpf())
				.build();
	}
}
