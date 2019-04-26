package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Provides a table definition with constraints and relations.
 *
 * @author jablonskiba
 */
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@OnDelete(action = OnDeleteAction.CASCADE)
@Entity
public class OutpatientAdmission extends Admission {

    @Setter
    @Getter
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Department department;

    @Setter
    @Getter
    @NotNull
    @Future
    @Column(nullable = false)
    private LocalDateTime visitDate;

    public OutpatientAdmission(Patient patient, Department department, LocalDateTime visitDate) {
        super(patient);
        this.department = department;
        this.visitDate = visitDate;
    }

}
