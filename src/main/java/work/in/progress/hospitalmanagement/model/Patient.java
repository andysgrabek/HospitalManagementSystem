package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Provides an inherited table definition from {@link Person} with constraints and
 * relations.
 *
 * @author jablonskiba
 */
@ToString(callSuper = true, exclude = "currentAdmission")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Patient extends Person {

    @Getter
    @Basic
    @NotNull
    @PastOrPresent
    @Column(nullable = false, updatable = false)
    private LocalDate birthDate;
    @Getter
    @Setter
    @Size(min = 5, max = 15)
    @Pattern(regexp = "^(0|[1-9][0-9]*)$")
    @Column(nullable = false)
    private String phoneNumber;
    @Getter
    @Setter
    @NotNull
    @Column(nullable = false)
    private boolean isAlive;
    @Getter
    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private Address homeAddress;
    @Setter
    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Admission currentAdmission;

    @Builder
    public Patient(String name, String surname, LocalDate birthDate, String phoneNumber,
                   boolean isAlive, Address homeAddress, Admission currentAdmission) {
        super(name, surname);
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.isAlive = isAlive;
        this.homeAddress = homeAddress;
        this.currentAdmission = currentAdmission;
    }

    public Optional<Admission> getCurrentAdmission() {
        return Optional.ofNullable(currentAdmission);
    }

}
