package work.in.progress.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.repository.BedRepository;

import java.util.Collection;

/**
 * Provides a public API to manage {@link Bed}.
 *
 * @author jablonskiba
 */
@Service
public class BedService extends AbstractService<Bed, Integer> {

    private final BedRepository bedRepository;

    @Autowired
    public BedService(BedRepository bedRepository) {
        super(bedRepository);
        this.bedRepository = bedRepository;
    }

    /**
     * Retrieves beds witch are currently assigned to a Patient
     * {@link work.in.progress.hospitalmanagement.model.Patient} for a given Department
     * {@link Department}}.
     *
     * @param department the department owning beds
     * @return beds
     */
    public Collection<Bed> occupiedBeds(Department department) {
        return bedRepository.occupiedBeds(department);
    }

    @Override
    protected Sort defaultSort() {
        return Sort.by("roomNumber");
    }

}
