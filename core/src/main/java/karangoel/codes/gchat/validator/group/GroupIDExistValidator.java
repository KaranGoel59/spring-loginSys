package karangoel.codes.gchat.validator.group;

import karangoel.codes.gchat.model.UserGroup;
import karangoel.codes.gchat.service.UserGroupService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@NoArgsConstructor
@Component
public class GroupIDExistValidator implements ConstraintValidator<GroupIDExist,Long> {

    // == fields ==
    private  UserGroupService userGroupService;

    // == setters ==
    @Autowired
    public void setUserGroupService(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

    @Override
    public boolean isValid(Long id , ConstraintValidatorContext context) {
        if(Objects.isNull(id)) {
            return  false;
        }
        UserGroup userGroup = userGroupService.getGroup(id);
        return userGroup != null;
    }
}
