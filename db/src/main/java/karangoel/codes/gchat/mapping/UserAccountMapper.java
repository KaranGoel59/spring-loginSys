package karangoel.codes.gchat.mapping;

import karangoel.codes.gchat.domain.UserAccountEntity;
import karangoel.codes.gchat.model.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserAccountMapper {
    // == implemented by mapstruct == //

    UserAccount toUser(UserAccountEntity userAccountEntity);
    UserAccountEntity toUserEntity(UserAccount userAccount);
}
