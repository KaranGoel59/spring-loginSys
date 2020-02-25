package karangoel.codes.gchat.unit.mapper;

import karangoel.codes.gchat.domain.UserAccountEntity;
import karangoel.codes.gchat.mapping.UserAccountMapper;
import karangoel.codes.gchat.model.UserAccount;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAccountMapperTest {
    private UserAccountMapper userAccountMapper = Mappers.getMapper(UserAccountMapper.class);

    @Test
    public void account_To_Entity() {
        UserAccount userAccount = UserAccount.builder()
                .id((long) 1)
                .email("test@test.com")
                .firstName("test")
                .lastName("test")
                .password("test")
                .build();

        // when
        UserAccountEntity userAccountEntity = userAccountMapper.toUserEntity(userAccount);
        // then
        assertThat(userAccount.getEmail()).isEqualTo(userAccountEntity.getEmail());
    }

    @Test
    public void entity_To_Account() {
        UserAccountEntity userAccountEntity = UserAccountEntity.builder()
                .id((long) 1)
                .email("test@test.com")
                .firstName("test")
                .lastName("test")
                .password("test")
                .build();

        // when
        UserAccount userAccount = userAccountMapper.toUser(userAccountEntity);
        // then
        assertThat(userAccountEntity.getEmail()).isEqualTo(userAccount.getEmail());
    }
}
