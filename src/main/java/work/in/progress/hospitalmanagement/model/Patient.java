package work.in.progress.hospitalmanagement.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class Patient extends Person {

    @Builder
    public Patient(String name, String surname, LocalDate birthDate, String phoneNumber, boolean isAlive, Address homeAddress, Admission currentAdmission) {
        super(name, surname);
        this.birthDate = birthDate;
        this.isAlive = isAlive;
        this.homeAddress = homeAddress;
        this.currentAdmission = currentAdmission;
    }

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
    @NotNull
    private boolean isAlive;

    @Getter
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private Address homeAddress;

    @Getter
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Admission currentAdmission;

}
