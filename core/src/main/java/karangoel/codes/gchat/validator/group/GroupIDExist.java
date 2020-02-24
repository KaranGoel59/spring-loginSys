package karangoel.codes.gchat.validator.group;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = GroupIDExistValidator.class)
public @interface GroupIDExist {
    String message() default "{user.group.not_exist}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
