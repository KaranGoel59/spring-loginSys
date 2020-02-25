package karangoel.codes.gchat.service;

import karangoel.codes.gchat.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // == fields ==
    private final UserAccountService userAccountService;

    // == constructors ==
    @Autowired
    public UserDetailsServiceImpl(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    // == public methods ==
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountService.getUser(email);
        if (userAccount == null) {
            throw new UsernameNotFoundException(email);
        }

        return new User(userAccount.getEmail(), userAccount.getPassword(), Collections.emptyList());
    }
}
