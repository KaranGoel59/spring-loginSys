package karangoel.codes.gchat.repository;

import karangoel.codes.gchat.domain.UserGroupEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserGroupRepository extends CrudRepository<UserGroupEntity,Long> {
    // == implemented by jpa == //
}
