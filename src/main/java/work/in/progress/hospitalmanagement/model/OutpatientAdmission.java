package work.in.progress.hospitalmanagement.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class OutpatientAdmission extends Admission {

    public OutpatientAdmission(Patient patient, Department department, LocalDate admissionDate) {
        this.patient = patient;
        this.department = department;
        this.admissionDate = admissionDate;
    }

    @Setter
    @Getter
    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

}
