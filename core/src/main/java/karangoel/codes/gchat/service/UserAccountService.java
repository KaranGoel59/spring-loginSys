/*
interface for User Service
 */

package karangoel.codes.gchat.service;

import karangoel.codes.gchat.model.UserAccount;

public interface UserAccountService {
    UserAccount saveUser(UserAccount newUserAccount);
    UserAccount getUser(String email);
}
