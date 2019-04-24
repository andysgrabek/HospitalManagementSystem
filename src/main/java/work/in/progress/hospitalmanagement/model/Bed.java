package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

/**
 * Provides a table definition with constraints and relations.
 *
 * @author jablonskiba
 */
@ToString(exclude = "id")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Bed {

    @Id
    @GeneratedValue
    private Integer id;
    @Getter
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    @Setter
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private InpatientAdmission admission;
    @Getter
    @Setter
    @NotBlank
    @Size(max = 15)
    @Column(length = 15, nullable = false)
    private String roomNumber;

    public Bed(Department department, String roomNumber) {
        this.department = department;
        this.roomNumber = roomNumber;
    }

    public Optional<InpatientAdmission> getAdmission() {
        return Optional.ofNullable(admission);
    }

}
