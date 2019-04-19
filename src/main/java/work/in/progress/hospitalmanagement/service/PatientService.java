package work.in.progress.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.repository.PatientRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Provides a public API to manage {@link Patient}.
 *
 * @author jablonskiba
 */
@Service
public class PatientService extends AbstractService<Patient, Integer> {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        super(patientRepository);
        this.patientRepository = patientRepository;
    }

    /**
     * Retrieves a patients with a specified name {@see Patient#getName()}.
     *
     * @param name search criteria
     * @return all patients with a specified name
     */
    public List<Patient> findByName(String name) {
        return patientRepository.findByName(name);
    }

    /**
     * Retrieves a patients with a specified surname {@see Patient#getSurname()}.
     *
     * @param surname search criteria
     * @return all patients with a specified surname
     */
    public List<Patient> findBySurname(String surname) {
        return patientRepository.findBySurname(surname);
    }

    /**
     * Retrieves a patients with a specified birth date {@see Patient#getBirthDate()}.
     *
     * @param birthDate search criteria
     * @return all patients with a specified birth date
     */
    public List<Patient> findByBirthDate(LocalDate birthDate) {
        return patientRepository.findByBirthDate(birthDate);
    }

    @Override
    protected Sort defaultSort() {
        return Sort.by("id");
    }

}
