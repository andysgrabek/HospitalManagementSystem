package work.in.progress.hospitalmanagement.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.repository.DepartmentRepository;

@RunWith(SpringRunner.class)
public class DepartmentServiceTest {

    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @Before
    public void setUp() {
        departmentService = new DepartmentService(departmentRepository);
    }

    @Test
    public void emptyTest() {
    }

}
