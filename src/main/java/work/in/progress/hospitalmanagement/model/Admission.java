package work.in.progress.hospitalmanagement.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Admission {

    @Id
    @GeneratedValue
    private Integer id;

    @CreatedDate
    protected LocalDate admissionDate;

    @Getter
    @OneToOne(optional = false)
    protected Patient patient;

    public abstract Department getDepartment();

}
