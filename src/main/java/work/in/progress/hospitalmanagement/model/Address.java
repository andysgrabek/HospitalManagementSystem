package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

/**
 * Provides a table definition with constraints and relations.
 *
 * @author jablonskiba
 */
@ToString(exclude = "id")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Address {

    @Id
    @GeneratedValue
    private Integer id;
    @Getter
    @Setter
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String addressLine;
    @Getter
    @Setter
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String city;
    @Getter
    @Setter
    @PositiveOrZero
    @Range(min = 1000, max = 99999)
    @Column(nullable = false)
    private int zipCode;

    public Address(String addressLine, String city, int zipCode) {
        this.addressLine = addressLine;
        this.city = city;
        this.zipCode = zipCode;
    }

}
