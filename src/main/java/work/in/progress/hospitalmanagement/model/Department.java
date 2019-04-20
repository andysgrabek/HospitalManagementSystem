package work.in.progress.hospitalmanagement.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides a table definition with constraints and relations.
 *
 * @author jablonskiba
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Department {

    @Id
    @GeneratedValue
    private Integer id;
    @Getter
    @NotBlank
    @Column(nullable = false, updatable = false)
    private String name;
    @Getter
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private Address address;
    @Getter
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bed> beds = new HashSet<>();

    public Department(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("%s in %s with beds %s", name, address, beds);
    }

}
