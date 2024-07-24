package br.com.healthemed.healthhuman.application.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonInclude(Include.NON_NULL)
public class DoctorDto extends UserDto {
	
	private String crm;
	
	private String speciality;
	
	private String zipCode;
	
	private String address;
	
	private String complement;
	
	private Integer rating;
	
	private Double price;
	
	private Double latitude;
	
	private Double longitude;	
	
	private Double distanceInKm;
}
