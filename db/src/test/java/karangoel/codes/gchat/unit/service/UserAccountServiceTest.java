package karangoel.codes.gchat.unit.service;

import karangoel.codes.gchat.domain.UserAccountEntity;
import karangoel.codes.gchat.model.UserAccount;
import karangoel.codes.gchat.repository.UserAccountRepository;
import karangoel.codes.gchat.service.UserAccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTest {

    @Mock
    UserAccountRepository userAccountRepository;

    @InjectMocks
    UserAccountServiceImpl userAccountService;


    @Test
    public void whenGetUser_returnUser() {
        Optional<UserAccountEntity> userAccountEntity = Optional.ofNullable(
                UserAccountEntity.builder()
                .id(1)
                .email("test@test.com")
                .firstName("test")
                .lastName("test")
                .password("test")
                .build()
        );

        doReturn(userAccountEntity).when(userAccountRepository).findUserEntityByEmail("test@test.com");

        // when
        UserAccount userAccount = userAccountService.getUser("test@test.com");
        // then
        assertThat(userAccountEntity.isPresent()).isTrue();
        assertThat(userAccount.getId()).isEqualTo(userAccountEntity.get().getId());
    }

}
