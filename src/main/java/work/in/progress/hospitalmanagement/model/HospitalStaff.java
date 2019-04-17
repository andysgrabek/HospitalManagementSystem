package work.in.progress.hospitalmanagement.model;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Getter
    @Email
    @Formula("CONCAT(LOWER(SUBSTRING(name, 1, 4)), LOWER(SUBSTRING(surname, 1, 4)), id, '@dtu.dk')")
    private String email;

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
