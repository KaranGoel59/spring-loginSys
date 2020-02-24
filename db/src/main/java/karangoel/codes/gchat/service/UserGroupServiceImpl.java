package karangoel.codes.gchat.service;

import karangoel.codes.gchat.domain.UserAccountEntity;
import karangoel.codes.gchat.domain.UserGroupEntity;
import karangoel.codes.gchat.mapping.UserGroupMapper;
import karangoel.codes.gchat.model.UserGroup;
import karangoel.codes.gchat.repository.UserAccountRepository;
import karangoel.codes.gchat.repository.UserGroupRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    // == fields ==
    private UserGroupMapper userGroupMapper = Mappers.getMapper(UserGroupMapper.class);

    private UserGroupRepository userGroupRepository;
    private UserAccountRepository userAccountRepository;

    // == constructors ==
    @Autowired
    public UserGroupServiceImpl(UserGroupRepository userGroupRepository, UserAccountRepository userAccountRepository) {
        this.userGroupRepository = userGroupRepository;
        this.userAccountRepository = userAccountRepository;
    }

    // == public methods ==
    @Override
    public UserGroup createGroup(UserGroup newGroup, String userEmail) {
        UserGroupEntity newGroupEntity = userGroupRepository.save(userGroupMapper.toGroupEntity(newGroup));
        addMember(userEmail,newGroupEntity.getId());
        return userGroupMapper.toGroup(newGroupEntity);
    }

    @Override
    public UserGroup addMember(String userEmail, long groupId) {
        Optional<UserGroupEntity> groupEntity = userGroupRepository.findById(groupId);
        Optional<UserAccountEntity> userEntity = userAccountRepository.findUserEntityByEmail(userEmail);

        if (groupEntity.isPresent() && userEntity.isPresent()) {
           // add user to group
            groupEntity.get()
                    .getUsers()
                    .add(userEntity.get());
            userGroupRepository.save(groupEntity.get());

            // add group to user
            userEntity.get()
                    .getGroups()
                    .add(groupEntity.get());
            userAccountRepository.save(userEntity.get());

            return userGroupMapper.toGroup(groupEntity.get());
        } else {
            return null;
        }
    }

    @Override
    public UserGroup getGroup(Long id) {
        Optional<UserGroupEntity> userGroupEntity = userGroupRepository.findById(id);
        return userGroupEntity
                .map(groupEntity -> userGroupMapper.toGroup(groupEntity))
                .orElse(null);
    }

    @Override
    public Set<UserGroup> getGroups(String userEmail) {
        Optional<UserAccountEntity> userAccountEntity = userAccountRepository.findUserEntityByEmail(userEmail);
        return userAccountEntity
                .map(accountEntity -> userGroupMapper.toGroups(accountEntity.getGroups()))
                .orElse(null);
    }
}
