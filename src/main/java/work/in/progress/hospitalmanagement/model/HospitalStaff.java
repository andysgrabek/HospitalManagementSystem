package work.in.progress.hospitalmanagement.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
public class HospitalStaff extends Person {

    public enum Role {
        DOCTOR, NURSE, CLERK, ICT_OFFICER;
    }

    @Builder
    public HospitalStaff(String name, String surname, Role role, Department department) {
        super(name, surname);
        this.role = role;
        this.department = department;
    }

    @Getter
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @Email
    @Transient
    public String getEmail() {
        return String.format("%s.%s_%d@dtu.dk", StringUtils.left(name, 4), StringUtils.left(surname, 4), id);
    }

    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Override
    public String toString() {
        return String.format("%s %s (%s)\n%s", name, surname, role, department);
    }

}
