package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Provides a table definition with constraints and relations.
 *
 * @author jablonskiba
 */
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class OutpatientAdmission extends Admission {

    @Setter
    @Getter
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    public OutpatientAdmission(Patient patient, Department department) {
        super(patient);
        this.department = department;
    }

}
