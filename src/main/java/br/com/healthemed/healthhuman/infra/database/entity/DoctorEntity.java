package br.com.healthemed.healthhuman.infra.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DoctorEntity extends UserEntity {
	
	private static final long serialVersionUID = 4781858089323528412L;
	
	@NonNull
	@NotNull
	@Column(name = "CRM", columnDefinition = "varchar(15)")
	private String crm;
	
	@NonNull
	@NotNull
	@Column(name = "SPECIALITY", columnDefinition = "varchar(100)")
	private String speciality;
	
	@NonNull
	@NotNull
	@Column(name = "ZIP_CODE")
	private String zipCode;
	
	@NonNull
	@NotNull
	@Column(name = "ADDRESS")
	private String address;
	
	@NonNull
	@NotNull
	@Column(name = "ADDRESS_NUMBER")
	private Integer number;
	
	@NonNull
	@NotNull
	@Column(name = "ADDRESS_COMPLEMENT")
	private String complement;
	
	@NonNull
	@NotNull
	@Column(name = "ADDRESS_CITY")
	private String city;
	
	@NonNull
	@NotNull
	@Column(name = "ADDRESS_STATE")
	private String state;
	
	@NonNull
	@NotNull
	@Column(name = "ADDRESS_COUNTRY")
	private String country;
	
	@NonNull
	@NotNull
	@Column(name = "RATING")
	private Integer rating;
	
	@NonNull
	@NotNull
	@Column(name = "PRICE")
	private Double price;
	
	@Setter
	@Column(name = "LATITUDE")
	private double latitude;
	
	@Setter
	@Column(name = "LONGITUDE")
	private double longitude;
	
	public String getSearchableAddress() {
		return String.format("%s, %s %s %s %s", address, number, city, state, country);
	}

	public String getFullAddress() {
		return String.format("%s, %s - %s, %s, %s", address, number, city, state, country);
	}
}
