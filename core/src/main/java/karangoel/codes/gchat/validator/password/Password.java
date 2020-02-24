package karangoel.codes.gchat.validator.password;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password  {
    String message() default "{user.password.invalid}";;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
