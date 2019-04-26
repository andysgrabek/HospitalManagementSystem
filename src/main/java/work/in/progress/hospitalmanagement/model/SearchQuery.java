package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import work.in.progress.hospitalmanagement.constraint.SqlExpression;

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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class SearchQuery {

    @Getter
    @NotBlank
    @Id
    @Size(max = 100)
    @Column(length = 100, nullable = false, updatable = false)
    private String label;

    @Getter
    @SqlExpression
    @NotBlank
    @Size(max = 300)
    @Column(length = 300, nullable = false)
    private String expression;

}
