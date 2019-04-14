package work.in.progress.hospitalmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
public class Patient extends Person {

    @Getter
    @Basic
    @NotNull
    @PastOrPresent
    private LocalDate birthDate;

    @Getter
    @Setter
    @Size(min = 5, max = 15)
    @Pattern(regexp = "^(0|[1-9][0-9]*)$")
    private String phoneNumber;

    @Getter
    @Setter
    private boolean isAlive;

    @Getter
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private Address homeAddress;

    @Getter
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Admission currentAdmission;

}
