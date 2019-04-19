package work.in.progress.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import work.in.progress.hospitalmanagement.model.InpatientAdmission;
import work.in.progress.hospitalmanagement.repository.InpatientAdmissionRepository;

public class InpatientAdmissionService extends AbstractService<InpatientAdmission, Integer> {

    @Autowired
    public InpatientAdmissionService(InpatientAdmissionRepository inpatientAdmissionRepository) {
        super(inpatientAdmissionRepository);
    }

    @Override
    protected Sort defaultSort() {
        return Sort.by("admissionDate");
    }

}
