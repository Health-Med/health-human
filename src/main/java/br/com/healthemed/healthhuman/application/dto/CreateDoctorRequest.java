package br.com.healthemed.healthhuman.application.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonInclude(Include.NON_NULL)
public class CreateDoctorRequest implements Serializable {
	
	private static final long serialVersionUID = -621830335594903665L;

	public String id;
	
	@NonNull
	@NotNull
	public String name;
	
	@NonNull
	@NotNull
	public String crm;
	
	@NonNull
	@NotNull
	public String speciality;
	
	@NonNull
	@NotNull
	public String zipCode;
	
	@NonNull
	@NotNull
	public String address;
	
	@NonNull
	@NotNull
	public Integer number;
	
	@NonNull
	@NotNull
	public String complement;
	
	@NonNull
	@NotNull
	public String city;
	
	@NonNull
	@NotNull
	public String state;
	
	@NonNull
	@NotNull
	public String country;
	
	@NonNull
	@NotNull
	public Integer rating;
	
	public Double price;
}
