package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Provides a table definition with constraints and relations.
 *
 * @author jablonskiba
 */
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class InpatientAdmission extends Admission {

    @Getter
    @OneToOne(optional = false)
    private Bed bed;

    public InpatientAdmission(Patient patient, Bed bed) {
        super(patient);
        this.bed = bed;
    }

    @Override
    public Department getDepartment() {
        return bed.getDepartment();
    }

}
