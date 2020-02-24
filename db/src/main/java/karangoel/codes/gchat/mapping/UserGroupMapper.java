package karangoel.codes.gchat.mapping;

import karangoel.codes.gchat.domain.UserGroupEntity;
import karangoel.codes.gchat.model.UserGroup;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserGroupMapper {
    // == implemented by mapstruct == //

    UserGroup toGroup(UserGroupEntity userGroupEntity);
    UserGroupEntity toGroupEntity(UserGroup userGroup);
    Set<UserGroup> toGroups(Set<UserGroupEntity> userGroupEntities);
}
