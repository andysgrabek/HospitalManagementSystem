package work.in.progress.hospitalmanagement.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
public class InpatientAdmission extends Admission {

    public InpatientAdmission(Patient patient, Bed bed, LocalDate admissionDate) {
        this.patient = patient;
        this.bed = bed;
        this.admissionDate = admissionDate;
    }

    @Getter
    @OneToOne(optional = false)
    private Bed bed;

    @Override
    public Department getDepartment() {
        return bed.getDepartment();
    }

}
