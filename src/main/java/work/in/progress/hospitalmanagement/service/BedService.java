package work.in.progress.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.repository.BedRepository;

import java.util.Collection;

@Service
public class BedService {

    private final BedRepository bedRepository;

    @Autowired
    public BedService(BedRepository bedRepository) {
        this.bedRepository = bedRepository;
    }

    public Collection<Bed> occupiedBeds(Department department) {
        return bedRepository.occupiedBeds(department);
    }

}
