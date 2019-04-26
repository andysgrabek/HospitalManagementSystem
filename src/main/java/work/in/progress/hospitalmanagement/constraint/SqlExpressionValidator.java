package work.in.progress.hospitalmanagement.constraint;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.hibernate.jpa.QueryHints.HINT_READONLY;

/**
 * Defines the logic to validate a SQL expression constraint
 * for a type {@code String}.
 *
 * @author jablonskiba
 */
public class SqlExpressionValidator implements ConstraintValidator<SqlExpression, String> {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Autowired
    public SqlExpressionValidator(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Initializes the validator in preparation for
     * {@link #isValid(String, ConstraintValidatorContext)} calls.
     * The constraint annotation for a given constraint declaration
     * is passed.
     * <p>
     * This method is guaranteed to be called before any use of this instance for
     * validation.
     * <p>
     * Creates a new application-managed EntityManager used in {@link #isValid(String, ConstraintValidatorContext)}.
     *
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(SqlExpression constraintAnnotation) {
        entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method tries to execute as passed expression in a transaction
     * on a current database instance. Any changes are roll backed.
     *
     * @param value   expression to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            entityManager.getTransaction().begin();
            final Query validatedQuery = entityManager.createQuery(value);
            validatedQuery.setHint(HINT_READONLY, true);
            validatedQuery.getResultList();
        } catch (Exception e) {
            return false;
        } finally {
            entityManager.getTransaction().rollback();
        }
        return true;
    }

}
