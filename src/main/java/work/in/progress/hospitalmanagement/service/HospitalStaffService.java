package work.in.progress.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import work.in.progress.hospitalmanagement.model.HospitalStaff;
import work.in.progress.hospitalmanagement.repository.HospitalStaffRepository;

import java.util.List;
import java.util.Optional;

/**
 * Provides a public API to manage {@link HospitalStaff}.
 *
 * @author jablonskiba
 */
@Service
public class HospitalStaffService extends AbstractService<HospitalStaff, Integer> {

    private final HospitalStaffRepository hospitalStaffRepository;

    @Autowired
    public HospitalStaffService(HospitalStaffRepository hospitalStaffRepository) {
        super(hospitalStaffRepository);
        this.hospitalStaffRepository = hospitalStaffRepository;
    }

    @Override
    public HospitalStaff save(HospitalStaff entity) {
        HospitalStaff temp = hospitalStaffRepository.save(entity);
        return hospitalStaffRepository.findById(temp.getId()).orElseThrow(IllegalStateException::new);
    }

    /**
     * Retrieves a hospital staff entries with a specified name
     * {@see HospitalStaff#getName()}.
     *
     * @param name search criteria
     * @return all hospital staff entries with a specified name
     */
    public List<HospitalStaff> findByName(String name) {
        return hospitalStaffRepository.findByName(name);
    }

    /**
     * Retrieves a hospital staff entries with a specified name
     * {@see HospitalStaff#getSurname()}.
     *
     * @param surname search criteria
     * @return all hospital staff entries with a specified surname
     */
    public List<HospitalStaff> findBySurname(String surname) {
        return hospitalStaffRepository.findBySurname(surname);
    }

    /**
     * Retrieves a hospital staff entries with a specified email @see
     * HospitalStaff#getEmail()}.
     *
     * @param email search criteria
     * @return all hospital staff entries with a specified email
     */
    public Optional<HospitalStaff> findByEmail(String email) {
        return hospitalStaffRepository.findByEmail(email);
    }

    @Override
    protected Sort defaultSort() {
        return Sort.by("id");
    }

}
