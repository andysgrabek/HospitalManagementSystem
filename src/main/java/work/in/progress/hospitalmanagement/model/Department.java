package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Department {

    @Getter
    @NotBlank
    @Id
    @Size(max = 100)
    @Column(length = 100, nullable = false, updatable = false)
    private String name;

}
