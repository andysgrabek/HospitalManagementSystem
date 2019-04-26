package work.in.progress.hospitalmanagement.util;

import work.in.progress.hospitalmanagement.model.*;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides mock for all entity objects {@link work.in.progress.hospitalmanagement.model}
 * that should only be used in tests, when field details are not relevant or can be
 * mutated.
 *
 * @author jablonskiba
 */
public final class Mocks {

    /* Suppresses default constructor, ensuring noninstantiability. */
    private Mocks() {
    }

    public static Address address() {
        return new Address("Energy 1", "Copenhagen", 12345);
    }

    public static Department department() {
        return new Department("Intensive Care");
    }

    public static Patient patient() {
        return Patient.builder().name("John").surname("Smith")
                .birthDate(LocalDate.of(1410, 7, 15)).phoneNumber("123456789")
                .isAlive(true).homeAddress(address()).build();
    }

    public static HospitalStaff hospitalStaff() {
        return HospitalStaff.builder().name("Ann").surname("Williams")
                .role(HospitalStaff.Role.DOCTOR).department(department()).build();
    }

    public static Bed bed() {
        return new Bed(department(), "12E");
    }

    public static InpatientAdmission inpatientAdmission() {
        return new InpatientAdmission(patient(), bed());
    }

    public static OutpatientAdmission outpatientAdmission() {
        return new OutpatientAdmission(patient(), department());
    }

    public static SearchQuery searchQuery() {
        return new SearchQuery("All department names", "SELECT name FROM Department");
    }

    public static Validator validatorAlwaysCorrect() {
        return new Validator() {
            @Override
            public <T> Set<ConstraintViolation<T>> validate(T t, Class<?>... classes) {
                return new HashSet<>();
            }

            @Override
            public <T> Set<ConstraintViolation<T>> validateProperty(T t, String s, Class<?>... classes) {
                return new HashSet<>();
            }

            @Override
            public <T> Set<ConstraintViolation<T>> validateValue(Class<T> aClass, String s, Object o, Class<?>... classes) {
                return new HashSet<>();
            }

            @Override
            public BeanDescriptor getConstraintsForClass(Class<?> aClass) {
                return null;
            }

            @Override
            public <T> T unwrap(Class<T> aClass) {
                return null;
            }

            @Override
            public ExecutableValidator forExecutables() {
                return null;
            }
        };
    }

    public static Validator validatorAlwaysIncorrect() {
        return new Validator() {
            @Override
            public <T> Set<ConstraintViolation<T>> validate(T t, Class<?>... classes) {
                return getConstraintViolations();
            }

            @Override
            public <T> Set<ConstraintViolation<T>> validateProperty(T t, String s, Class<?>... classes) {
                return getConstraintViolations();
            }

            @Override
            public <T> Set<ConstraintViolation<T>> validateValue(Class<T> aClass, String s, Object o, Class<?>... classes) {
                return getConstraintViolations();
            }

            private <T> HashSet<ConstraintViolation<T>> getConstraintViolations() {
                ConstraintViolation<T> constraintViolation = new ConstraintViolation<T>() {
                    @Override
                    public String getMessage() {
                        return null;
                    }

                    @Override
                    public String getMessageTemplate() {
                        return null;
                    }

                    @Override
                    public T getRootBean() {
                        return null;
                    }

                    @Override
                    public Class<T> getRootBeanClass() {
                        return null;
                    }

                    @Override
                    public Object getLeafBean() {
                        return null;
                    }

                    @Override
                    public Object[] getExecutableParameters() {
                        return new Object[0];
                    }

                    @Override
                    public Object getExecutableReturnValue() {
                        return null;
                    }

                    @Override
                    public Path getPropertyPath() {
                        return null;
                    }

                    @Override
                    public Object getInvalidValue() {
                        return null;
                    }

                    @Override
                    public ConstraintDescriptor<?> getConstraintDescriptor() {
                        return null;
                    }

                    @Override
                    public <U> U unwrap(Class<U> aClass) {
                        return null;
                    }
                };
                HashSet<ConstraintViolation<T>> set = new HashSet<>();
                set.add(constraintViolation);
                return set;
            }

            @Override
            public BeanDescriptor getConstraintsForClass(Class<?> aClass) {
                return null;
            }

            @Override
            public <T> T unwrap(Class<T> aClass) {
                return null;
            }

            @Override
            public ExecutableValidator forExecutables() {
                return null;
            }
        };
    }

}
