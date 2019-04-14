package work.in.progress.hospitalmanagement.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
    private String roomNumber;

}