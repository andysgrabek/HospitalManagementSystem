package work.in.progress.hospitalmanagement.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

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
