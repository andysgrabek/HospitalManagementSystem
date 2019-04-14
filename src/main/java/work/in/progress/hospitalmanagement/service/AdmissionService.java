package work.in.progress.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.in.progress.hospitalmanagement.model.InpatientAdmission;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import work.in.progress.hospitalmanagement.repository.InpatientAdmissionRepository;
import work.in.progress.hospitalmanagement.repository.OutpatientAdmissionRepository;

@Service
public class AdmissionService {

    private final InpatientAdmissionRepository inpatientAdmissionRepository;
    private final OutpatientAdmissionRepository outpatientAdmissionRepository;

    @Autowired
    public AdmissionService(InpatientAdmissionRepository inpatientAdmissionRepository, OutpatientAdmissionRepository outpatientAdmissionRepository) {
        this.inpatientAdmissionRepository = inpatientAdmissionRepository;
        this.outpatientAdmissionRepository = outpatientAdmissionRepository;
    }

    public InpatientAdmission allocatePatient(InpatientAdmission admission) {
        return inpatientAdmissionRepository.save(admission);
    }

    public OutpatientAdmission callPatient(OutpatientAdmission admission) {
        return outpatientAdmissionRepository.save(admission);
    }

    public void dischargePatient(InpatientAdmission admission) {
        inpatientAdmissionRepository.delete(admission);
    }

    public void dischargePatient(OutpatientAdmission admission) {
        outpatientAdmissionRepository.delete(admission);
    }

}
