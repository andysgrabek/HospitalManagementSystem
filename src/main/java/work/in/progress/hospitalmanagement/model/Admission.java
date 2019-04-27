package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Provides a joined table definition with constraints and relations.
 *
 * @author jablonskiba
 */
@ToString(exclude = "id")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
public abstract class Admission {

    @Id
    @GeneratedValue
    private Integer id;
    @Getter
    @NotNull
    @CreatedDate
    @Column(nullable = false)
    private LocalDate admissionDate;
    @Getter
    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Patient patient;

    Admission(Patient patient) {
        this.patient = patient;
    }

    @PreRemove
    private void preRemove() {
        patient.setAdmission(null);
    }

    public abstract Department getDepartment();

}
