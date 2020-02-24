package karangoel.codes.gchat.validator.email;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = UserEmailSignInValidator.class)
public @interface UserEmailSignIn {
    String message() default "{user.email.not_exist}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
