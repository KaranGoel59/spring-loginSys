package karangoel.codes.gchat.validator.email;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = UserEmailSignUpValidator.class)
public @interface UserEmailSignUp {
    String message() default "{user.email.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
