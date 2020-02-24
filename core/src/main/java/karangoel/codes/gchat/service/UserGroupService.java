/*
interface for Group Service
 */

package karangoel.codes.gchat.service;

import karangoel.codes.gchat.model.UserGroup;

import java.util.Set;

public interface UserGroupService {
    UserGroup createGroup(UserGroup newGroup, String userEmail);
    UserGroup addMember(String userEmail, long groupId);
    UserGroup getGroup(Long id);
    Set<UserGroup> getGroups(String userEmail);
}
