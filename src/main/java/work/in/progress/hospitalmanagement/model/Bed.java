package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Provides a table definition with constraints and relations.
 *
 * @author jablonskiba
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Bed {

    public Bed(Department department, String roomNumber) {
        this.department = department;
        this.roomNumber = roomNumber;
    }

    @Id
    @GeneratedValue
    private Integer id;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Setter
    @Getter
    @OneToOne
    private InpatientAdmission admission;

    @Getter
    @Setter
    @NotBlank
    @Column(nullable = false)
    private String roomNumber;

    @Override
    public String toString() {
        return String.format("In %s room %s assigned to %s",
                department, roomNumber, ObjectUtils.defaultIfNull(admission, "None"));
    }
}