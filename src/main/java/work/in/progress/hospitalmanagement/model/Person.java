package work.in.progress.hospitalmanagement.model;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class Person {

    Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    protected Integer id;

    @Getter
    @NotBlank
    @Column(nullable = false, updatable = false)
    protected String name;

    @Getter
    @NotBlank
    @Column(nullable = false, updatable = false)
    protected String surname;

}
