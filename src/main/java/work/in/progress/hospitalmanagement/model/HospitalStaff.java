package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Provides an inherited table definition from {@link Person} with constraints and
 * relations.
 *
 * @author jablonskiba
 */
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class HospitalStaff extends Person {

    @Getter
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
    /**
     * A unique computed email which consists of first for lowercase @{link name}
     * characters, first for lowercase @{link surname} characters, @{link id} and
     * '@dtu.dk' suffix.
     */
    @Getter
    @Email
    @Formula("CONCAT(LOWER(SUBSTRING(name, 1, 4)), LOWER(SUBSTRING(surname, 1, 4)), id, '@dtu.dk')")
    private String email;
    @Getter
    @Setter
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Builder
    public HospitalStaff(String name, String surname, Role role, Department department) {
        super(name, surname);
        this.role = role;
        this.department = department;
    }

    public enum Role {

        DOCTOR, NURSE, CLERK, ICT_OFFICER

    }

}
