package karangoel.codes.gchat.validator.password;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@NoArgsConstructor
@Component
public class PasswordValidator implements ConstraintValidator<Password,String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if(Objects.isNull(password)) {
            return false;
        }
        context.disableDefaultConstraintViolation();

        if (password.length() < 8) {
            context
                    .buildConstraintViolationWithTemplate("{user.password.min}")
                    .addConstraintViolation();
            return false;
        }

        if(password.length() > 100) {
            context
                    .buildConstraintViolationWithTemplate("{user.password.max}")
                    .addConstraintViolation();
            return false;
        }

        boolean upperCase, lowerCase, digit,  space;
        upperCase = lowerCase = digit = space =false;;
        for (char s : password.toCharArray()) {
            if(Character.isUpperCase(s)) {
                upperCase = true;
            }
            if(Character.isLowerCase(s)) {
                lowerCase = true;
            }
            if(Character.isDigit(s)) {
                digit = true;
            }
            if(Character.isSpaceChar(s)) {
                space = true;
            }
        }

        String fails = new String("");

        if(!upperCase) {
            fails += "{user.password.uppercase},";
        }
        if(!lowerCase) {
            fails += "{user.password.lowercase},";
        }
        if(!digit) {
            fails += "{user.password.digit},";
        }
        if(space) {
            fails += "{user.password.space},";
        }

        if(!fails.equals("")) {
            context
                    .buildConstraintViolationWithTemplate(fails)
                    .addConstraintViolation();
            return false;
        }

        return true;

    }
}
