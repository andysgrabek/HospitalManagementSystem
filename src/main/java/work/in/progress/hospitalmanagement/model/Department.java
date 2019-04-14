package work.in.progress.hospitalmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue
    private Integer id;

    @Getter
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private Address address;

    @Getter
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bed> beds;

}
