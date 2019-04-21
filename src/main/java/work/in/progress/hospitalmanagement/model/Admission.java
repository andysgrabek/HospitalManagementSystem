package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
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
abstract class Admission {

    @Id
    @GeneratedValue
    private Integer id;
    @Getter
    @CreatedDate
    @Column(nullable = false)
    private LocalDate admissionDate;
    @Getter
    @OneToOne(optional = false)
    private Patient patient;

    Admission(Patient patient) {
        this.patient = patient;
    }

    public abstract Department getDepartment();

}
