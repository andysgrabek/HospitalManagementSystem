package work.in.progress.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.model.Department;

import java.util.Collection;

public interface BedRepository extends JpaRepository<Bed, Integer> {
    @Query("SELECT bed FROM Bed bed WHERE bed.department = ?1 AND bed.admission IS NOT NULL")
    Collection<Bed> occupiedBeds(Department department);
}
