package work.in.progress.hospitalmanagement.validator;

import com.jfoenix.validation.base.ValidatorBase;
import lombok.Getter;

import javax.validation.Validator;

abstract class AbstractFieldValidator extends ValidatorBase {
    @Getter
    private Validator validator;
    @Getter
    private String field;
    @Getter
    private Class c;

    AbstractFieldValidator(Class c, String field, Validator validator) {
        this.c = c;
        this.field = field;
        this.validator = validator;
    }

}
