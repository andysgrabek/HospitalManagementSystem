package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Address {

    public Address(String addressLine, String city, int zipCode) {
        this.addressLine = addressLine;
        this.city = city;
        this.zipCode = zipCode;
    }

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
    @Max(99999)
    @Column(nullable = false)
    private int zipCode;

    @Override
    public String toString() {
        return String.format("%s\n%s - %d", addressLine, city, zipCode);
    }

}
