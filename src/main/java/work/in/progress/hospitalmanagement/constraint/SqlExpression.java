package work.in.progress.hospitalmanagement.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated element must a valid SQL expression that can be run on
 * a current database instance. Accepts {@code String}.
 *
 * @author jablonskiba
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SqlExpressionValidator.class)
@Documented
public @interface SqlExpression {

    String message() default "{work.in.progress.hospitalmanagement.constraint.SqlExpression.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@code @SqlExpression} constraints on the same element.
     *
     * @see SqlExpression
     */
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        SqlExpression[] value();
    }

}
