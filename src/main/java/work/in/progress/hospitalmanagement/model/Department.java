package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Provides a table definition with constraints and relations.
 *
 * @author jablonskiba
 */
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Department {

    @Getter
    @NotBlank
    @Id
    @Size(max = 100)
    @Column(length = 100, nullable = false, updatable = false)
    private String name;

    public Department(String name) {
        this.name = name;
    }

}
