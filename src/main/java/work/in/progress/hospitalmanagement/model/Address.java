package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

/**
 * Provides a table definition with constraints and relations.
 *
 * @author jablonskiba
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Address {

    @Id
    @GeneratedValue
    private Integer id;
    @Getter
    @Setter
    @NotBlank
    @Column(nullable = false)
    private String addressLine;
    @Getter
    @Setter
    @NotBlank
    @Column(nullable = false)
    private String city;
    @Getter
    @Setter
    @PositiveOrZero
    @SuppressWarnings("checkstyle:MagicNumber")
    @Digits(integer = 5, fraction = 0)
    @Column(nullable = false)
    private int zipCode;

    public Address(String addressLine, String city, int zipCode) {
        this.addressLine = addressLine;
        this.city = city;
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s - %d", addressLine, city, zipCode);
    }

}
