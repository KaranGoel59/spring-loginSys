package karangoel.codes.gchat.service;

import karangoel.codes.gchat.domain.UserAccountEntity;
import karangoel.codes.gchat.mapping.UserAccountMapper;
import karangoel.codes.gchat.model.UserAccount;
import karangoel.codes.gchat.repository.UserAccountRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    // == fields ==
    private final UserAccountMapper userAccountMapper = Mappers.getMapper(UserAccountMapper.class);

    private final UserAccountRepository userAccountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // == constructors ==
    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // == public methods ==
    @Override
    public UserAccount saveUser(UserAccount newUserAccount) {
        newUserAccount.setPassword(bCryptPasswordEncoder.encode(newUserAccount.getPassword()));
        UserAccountEntity userAccountEntity = userAccountMapper.toUserEntity(newUserAccount);
        return userAccountMapper.toUser(userAccountRepository.save(userAccountEntity));
    }

    @Override
    public UserAccount getUser(String email) {
        Optional<UserAccountEntity> userAccountEntity = userAccountRepository.findUserEntityByEmail(email);

        return userAccountEntity
                .map(accountEntity -> userAccountMapper.toUser(accountEntity))
                .orElse(null);
    }
}
