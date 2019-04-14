package work.in.progress.hospitalmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
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
    private Admission admission;

    @Getter
    @Setter
    @NotBlank
    private String roomNumber;

}