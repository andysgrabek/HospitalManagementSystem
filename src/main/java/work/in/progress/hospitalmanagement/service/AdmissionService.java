package work.in.progress.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.in.progress.hospitalmanagement.model.InpatientAdmission;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import work.in.progress.hospitalmanagement.repository.InpatientAdmissionRepository;
import work.in.progress.hospitalmanagement.repository.OutpatientAdmissionRepository;

/**
 * Provides a public API to manage {@link InpatientAdmission} and {@link OutpatientAdmission}.
 *
 * @author jablonskiba
 */
@Service
public class AdmissionService {

    private final InpatientAdmissionRepository inpatientAdmissionRepository;
    private final OutpatientAdmissionRepository outpatientAdmissionRepository;

    @Autowired
    public AdmissionService(InpatientAdmissionRepository inpatientAdmissionRepository, OutpatientAdmissionRepository outpatientAdmissionRepository) {
        this.inpatientAdmissionRepository = inpatientAdmissionRepository;
        this.outpatientAdmissionRepository = outpatientAdmissionRepository;
    }

    /**
     * Allocates a patient using inpatient admission {@link InpatientAdmission}. Use the returned admission for
     * further operations as the allocation operation might have changed the admission instance.
     *
     * @param admission the admission used to allocate, must not be {@code null}
     * @return the allocation admission, will never be {@code null}
     * @throws IllegalArgumentException if the specified allocation is {@code null}
     */
    public InpatientAdmission allocatePatient(InpatientAdmission admission) {
        return inpatientAdmissionRepository.save(admission);
    }

    /**
     * Call a patient using outpatient admission. Use the returned admission for
     * further operations as the allocation operation might have changed the admission instance.
     *
     * @param admission the admission used to call, must not be {@code null}
     * @return the called admission, will never be {@code null}
     * @throws IllegalArgumentException if the specified allocation is {@code null}
     */
    public OutpatientAdmission callPatient(OutpatientAdmission admission) {
        return outpatientAdmissionRepository.save(admission);
    }

    /**
     * Discharge a given patient using inpatient admission.
     *
     * @param admission the admission to discharge the patient, must not be {@code null}
     * @throws IllegalArgumentException if the specified admission is {@code null}
     */
    public void dischargePatient(InpatientAdmission admission) {
        inpatientAdmissionRepository.delete(admission);
    }

    /**
     * Discharge a given patient using outpatient admission.
     *
     * @param admission the admission to discharge the patient, must not be {@code null}
     * @throws IllegalArgumentException if the specified admission is {@code null}
     */
    public void dischargePatient(OutpatientAdmission admission) {
        outpatientAdmissionRepository.delete(admission);
    }

}
