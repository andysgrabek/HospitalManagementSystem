package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Provides a superclass to inherit to define a table.
 *
 * @author jablonskiba
 */
@ToString
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private Integer id;
    @Getter
    @NotBlank
    @Size(max = 255)
    @Pattern(regexp = "^[A-Za-z \\-]+$")
    @Column(nullable = false, updatable = false)
    private String name;
    @Getter
    @NotBlank
    @Size(max = 255)
    @Pattern(regexp = "^[A-Za-z \\-]+$")
    @Column(nullable = false, updatable = false)
    private String surname;

    Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

}
