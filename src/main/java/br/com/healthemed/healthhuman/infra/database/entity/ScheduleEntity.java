package br.com.healthemed.healthhuman.infra.database.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.healthemed.healthhuman.domain.entity.ScheduleStatus;
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
@Table(schema = "healthemed", name = "TB_SCHEDULE")
@Builder
public class ScheduleEntity implements Serializable {
	
	private static final long serialVersionUID = 4781858089323528412L;

	@Id
	@Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
    private Long id;
	
	@NonNull
	@NotNull
	@Column(name = "DOCTOR_ID", columnDefinition = "varchar(100)")
	private String doctorId;
	
	@CreationTimestamp
    @Column(name = "CREATED_TIME")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "UPDATED_TIME")
    private LocalDateTime updatedTime;
	
	@Setter
	@NonNull
	@NotNull
	@Column(name = "SCHEDULE_DATE")
	private LocalDateTime schedule;
	
	@Setter
	@NonNull
	@NotNull
	@Column(name = "SCHEDULE_STATUS")
	private ScheduleStatus status;
	
	@Setter
	@Column(name = "CANCEL_JUSTIFICATION")
	private String justification;
	
	@Setter
	@Column(name = "PATIENT_ID")
	private Long patientId;
}
