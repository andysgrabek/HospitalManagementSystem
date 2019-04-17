package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

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
    protected LocalDate admissionDate;

    @Getter
    @OneToOne(optional = false)
    protected Patient patient;

    public abstract Department getDepartment();

    @Override
    public String toString() {
        return String.format("%s to %s on %s", patient, getDepartment(), admissionDate);
    }
}
