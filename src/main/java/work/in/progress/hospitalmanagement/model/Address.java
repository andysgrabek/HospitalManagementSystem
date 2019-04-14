package work.in.progress.hospitalmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Entity
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    private Integer id;

    @Getter
    @Setter
    @NotBlank
    private String city;
    @Getter
    @Setter
    @PositiveOrZero
    @Max(99999)
    private int zipCode;
    @Getter
    @Setter
    @NotBlank
    private String addressLine;

}
