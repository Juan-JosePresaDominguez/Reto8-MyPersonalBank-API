package es.netmind.mypersonalbankapi.constraints;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.Date;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidDate.Validator.class})
public @interface ValidDate {

    String message()
            default "Fecha inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<ValidDate, String> {
        @Override
        public void initialize(final ValidDate serial) {
        }

        @Override
        public boolean isValid(final String date, final ConstraintValidatorContext constraintValidatorContext) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date theDate = sdf.parse(date);
                Date now = new Date();
                int result = theDate.compareTo(now);

                return result < 0;
            } catch (Exception e) {
                return false;
            }

        }
    }
}
