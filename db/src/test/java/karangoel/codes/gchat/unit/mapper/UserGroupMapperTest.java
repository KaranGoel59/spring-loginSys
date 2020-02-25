package karangoel.codes.gchat.unit.mapper;

import karangoel.codes.gchat.domain.UserGroupEntity;
import karangoel.codes.gchat.mapping.UserGroupMapper;
import karangoel.codes.gchat.model.UserGroup;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;


public class UserGroupMapperTest {
    private UserGroupMapper userGroupMapper = Mappers.getMapper(UserGroupMapper.class);

    @Test
    public void group_To_Entity() {
        UserGroup userGroup = UserGroup.builder()
                .id((long) 1)
                .name("testGroup")
                .build();

        // when
        UserGroupEntity userGroupEntity = userGroupMapper.toGroupEntity(userGroup);
        // then
        assertThat(userGroup.getName()).isEqualTo(userGroupEntity.getName());
    }

    @Test
    public void entity_To_Group() {
        UserGroupEntity userGroupEntity = UserGroupEntity.builder()
                .id((long) 1)
                .name("testGroup")
                .build();

        // when
        UserGroup userGroup = userGroupMapper.toGroup(userGroupEntity);
        // then
        assertThat(userGroupEntity.getName()).isEqualTo(userGroup.getName());
    }
}
