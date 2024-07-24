package br.com.healthemed.healthhuman.infra.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PatientEntity extends UserEntity {
	
	private static final long serialVersionUID = 4781858089323528412L;
	
	@Column(name = "email", columnDefinition = "varchar(100)")
	private String email;
	
	@Column(name = "cpf")
	private String cpf;
}
