package karangoel.codes.gchat.integration.repository;

import karangoel.codes.gchat.domain.UserAccountEntity;
import karangoel.codes.gchat.repository.UserAccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserAccountRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Before
    public void setUp() {
        UserAccountEntity userAccountEntity = UserAccountEntity.builder()
                .firstName("test_first")
                .lastName("test_last")
                .email("test@test.com")
                .password("Test12345678")
                .build();

        testEntityManager.persist(userAccountEntity);
    }

    @Test
    public void whenFindByEmail_thenReturnUserAccount() {
        // when
        Optional<UserAccountEntity> userAccountEntity = userAccountRepository.findUserEntityByEmail("test@test.com");
        // then
        assertThat(userAccountEntity.isPresent()).isTrue();
        assertThat(userAccountEntity.get().getFirstName()).isEqualTo("test_first");
    }
}
