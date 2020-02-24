package karangoel.codes.gchat.validator.email;

import karangoel.codes.gchat.model.UserAccount;
import karangoel.codes.gchat.service.UserAccountService;
import karangoel.codes.gchat.utils.JSON.EmailRegexValidator;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@NoArgsConstructor
@Component
public class UserEmailSignInValidator implements ConstraintValidator<UserEmailSignUp,String> {

    // == fields ==
    UserAccountService userAccountService;

    // == setter ==
    @Autowired
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if(Objects.isNull(email) || !EmailRegexValidator.check(email)) {
            return false;
        }

        context.disableDefaultConstraintViolation();

        UserAccount userAccount = userAccountService.getUser(email);
        if (userAccount == null) {
            context
                    .buildConstraintViolationWithTemplate("{user.email.not_exist}")
                    .addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }
}
