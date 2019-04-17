package work.in.progress.hospitalmanagement.model;

import lombok.*;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Provides an inherited table definition from {@link Person} with constraints and relations.
 *
 * @author jablonskiba
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Patient extends Person {

    @Builder
    public Patient(String name, String surname, LocalDate birthDate, String phoneNumber, boolean isAlive, Address homeAddress, Admission currentAdmission) {
        super(name, surname);
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.isAlive = isAlive;
        this.homeAddress = homeAddress;
        this.currentAdmission = currentAdmission;
    }

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
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private Address homeAddress;

    @Getter
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Admission currentAdmission;

    @Override
    public String toString() {
        return String.format("%s %s (%s)\n%s\n%s\nassigned to %s", name, surname, isAlive ? "alive" : "dead",
                homeAddress, phoneNumber, ObjectUtils.defaultIfNull(currentAdmission, "None"));
    }

}
