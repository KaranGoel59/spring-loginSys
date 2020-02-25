package karangoel.codes.gchat.unit.service;

import karangoel.codes.gchat.domain.UserAccountEntity;
import karangoel.codes.gchat.domain.UserGroupEntity;
import karangoel.codes.gchat.model.UserGroup;
import karangoel.codes.gchat.repository.UserAccountRepository;
import karangoel.codes.gchat.repository.UserGroupRepository;
import karangoel.codes.gchat.service.UserGroupServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class UserGroupServiceTest {
    @Mock
    UserAccountRepository userAccountRepository;

    @Mock
    UserGroupRepository userGroupRepository;

    @InjectMocks
    UserGroupServiceImpl userGroupService;

    @Test
    public void whenAddMember_returnGroup() {
        UserAccountEntity userAccountEntity = UserAccountEntity.builder()
                .id((long) 1)
                .email("test@test.com")
                .firstName("test")
                .lastName("test")
                .password("test")
                .build();

        UserGroupEntity userGroupEntity = UserGroupEntity.builder()
                .id((long) 1)
                .name("testGroup")
                .build();

        doReturn(Optional.ofNullable(userAccountEntity)).when(userAccountRepository).findUserEntityByEmail("test@test.com");
        doReturn(Optional.ofNullable(userGroupEntity)).when(userGroupRepository).findById((long) 1);

        // when
        UserGroup userGroup = userGroupService.addMember("test@test.com",1);
        // then
        assertThat(userGroup.getName()).isEqualTo("testGroup");
    }
}
