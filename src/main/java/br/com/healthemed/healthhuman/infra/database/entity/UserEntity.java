package br.com.healthemed.healthhuman.infra.database.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@SuperBuilder
@AllArgsConstructor
@Table(schema = "healthemed", name = "TB_USER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class UserEntity implements Serializable {
	
	private static final long serialVersionUID = 4781858089323528412L;

	@Id
	@Setter
    @GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ID", columnDefinition = "varchar(100)")
    protected String id;
	
	@NonNull
	@NotNull
	@Column(name = "NAME", columnDefinition = "varchar(250)")
	protected String name;
	
	@NonNull
	@NotNull
	@Column(name = "USER_TYPE")
	@Enumerated(EnumType.STRING)
	protected UserType type;
}
