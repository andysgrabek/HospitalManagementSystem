package work.in.progress.hospitalmanagement.model;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Department {

    public Department(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Id
    @GeneratedValue
    private Integer id;

    @Getter
    @NotBlank
    private String name;

    @Getter
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private Address address;

    @Getter
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bed> beds = new HashSet<>();

    @Override
    public String toString() {
        return String.format("%s in %s with beds %s", name, address, beds);
    }
}
