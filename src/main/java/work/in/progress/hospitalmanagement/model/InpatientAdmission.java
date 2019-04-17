package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class InpatientAdmission extends Admission {

    public InpatientAdmission(Patient patient, Bed bed) {
        this.patient = patient;
        this.bed = bed;
    }

    @Getter
    @OneToOne(optional = false)
    private Bed bed;

    @Override
    public Department getDepartment() {
        return bed.getDepartment();
    }

}
