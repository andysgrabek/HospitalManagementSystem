package work.in.progress.hospitalmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@AllArgsConstructor
@Entity
public class InpatientAdmission extends Admission {

    @Getter
    @OneToOne(optional = false)
    private Bed bed;

    @Override
    public Department getDepartment() {
        return bed.getDepartment();
    }

}
