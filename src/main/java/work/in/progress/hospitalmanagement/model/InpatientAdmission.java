package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.validation.constraints.NotNull;

/**
 * Provides a table definition with constraints and relations.
 *
 * @author jablonskiba
 */
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@OnDelete(action = OnDeleteAction.CASCADE)
@Entity
public class InpatientAdmission extends Admission {

    @Getter
    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name = "bed_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Bed bed;

    public InpatientAdmission(Patient patient, Bed bed) {
        super(patient);
        this.bed = bed;
    }

    @PreRemove
    private void preRemove() {
        bed.setAdmission(null);
    }

    @Override
    public Department getDepartment() {
        return bed.getDepartment();
    }

}
