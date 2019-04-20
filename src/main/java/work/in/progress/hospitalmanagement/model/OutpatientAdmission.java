package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Provides a table definition with constraints and relations.
 *
 * @author jablonskiba
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class OutpatientAdmission extends Admission {

    @Setter
    @Getter
    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    public OutpatientAdmission(Patient patient, Department department) {
        super(patient);
        this.department = department;
    }

}
