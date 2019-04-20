package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

/**
 * Provides a table definition with constraints and relations.
 *
 * @author jablonskiba
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Bed {

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

    public Bed(Department department, String roomNumber) {
        this.department = department;
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return String.format("In %s room %s assigned to %s", department, roomNumber,
                ObjectUtils.defaultIfNull(admission, "None"));
    }

}
