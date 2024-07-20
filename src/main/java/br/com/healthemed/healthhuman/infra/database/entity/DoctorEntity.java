package br.com.healthemed.healthhuman.infra.database.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@AllArgsConstructor
@Table(schema = "healthemed", name = "TB_DOCTOR")
@Builder
public class DoctorEntity implements Serializable {
	
	private static final long serialVersionUID = 4781858089323528412L;

	@Id
	@Setter
    @GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ID", columnDefinition = "varchar(100)")
    private String id;
	
	@NonNull
	@NotNull
	@Column(name = "NAME", columnDefinition = "varchar(250)")
	private String name;
	
	@NonNull
	@NotNull
	@Column(name = "SPECIALITY", columnDefinition = "varchar(100)")
	private String speciality;
	
	@NonNull
	@NotNull
	@Column(name = "ZIP_CODE")
	private Long zipCode;
	
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
	@Column(name = "RATING")
	private Integer rating;
	
	@Setter
	@Column
	private double latitude;
	
	@Setter
	@Column
	private double longitude;

	public String getFullAddress() {
		return String.format("%s, %s", address, number);
	}
}
