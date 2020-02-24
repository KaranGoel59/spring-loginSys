package karangoel.codes.gchat.repository;

import karangoel.codes.gchat.domain.UserAccountEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAccountRepository extends CrudRepository<UserAccountEntity, Long> {
    // == implemented by jpa == //

    Optional<UserAccountEntity> findUserEntityByEmail(String email);
}
