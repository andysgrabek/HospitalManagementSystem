package work.in.progress.hospitalmanagement.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Address {

    public Address(String city, String addressLine, int zipCode) {
        this.city = city;
        this.addressLine = addressLine;
        this.zipCode = zipCode;
    }

    @Id
    @GeneratedValue
    private Integer id;

    @Getter
    @Setter
    @NotBlank
    private String city;

    @Getter
    @Setter
    @NotBlank
    private String addressLine;

    @Getter
    @Setter
    @PositiveOrZero
    @Max(99999)
    private int zipCode;

}
