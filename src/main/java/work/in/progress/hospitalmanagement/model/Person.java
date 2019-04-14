package work.in.progress.hospitalmanagement.model;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    Integer id;

    @Getter
    @NotBlank
    String name;

    @Getter
    @NotBlank
    String surname;

}
