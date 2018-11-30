package com.example.manualproject.service;

import com.example.manualproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserService {
    User findById(long id);

    User findByName(String name);

    User findByEmail(String email);

    User findBySocialId(String socialId);

    List<User> findAll();

    void save(User user);

    boolean confirm(String userName, String hash);

    void renameUser(String newName, long id);

    void changeEmail(String email, String userName, String hash);

    void changeEmailByAdmin(String email, long userId);

    void changePassword(String newPassword, long id);

    void saveSocialUser(String userName, String userId, String via);

    void authUser(User user);

    Set<GrantedAuthority> getAuthorities(User user);

    void delete(String[] delArray);

    void block(String[] blockArray);

    void unblock(String[] unblockArray);

    void addRole(String[] roleArray, String role);

    void delRole(String[] roleArray, String role);

    void logoutUser(long userId);


}
