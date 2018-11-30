package com.example.manualproject.repository;

import com.example.manualproject.model.Role;
import com.example.manualproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Set;


public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    User findByName(String name);

    User findByEmail(String email);

    User findBySocialid(String socialid);

    List<User> deleteById(long id);

    @Modifying
    @Query("update User u set u.confirmed = 1 where u.name = ?1")
    void confirm(String name);

    @Modifying
    @Query("update User u set u.blocked = 1 where u.id = ?1")
    void block(long id);

    @Modifying
    @Query("update User u set u.blocked = 0 where u.id = ?1")
    void unblock(long id);

    @Modifying
    @Query("update User u set u.name = ?1 where u.id = ?2")
    void renameUser(String name, long id);

    @Modifying
    @Query("update User u set u.password = ?1 where u.id = ?2")
    void changePassword(String password, long id);

    @Modifying
    @Query("update User u set u.email = ?1 where u.name = ?2")
    void changeEmail(String email, String name);

    @Modifying
    @Query("update User u set u.email = ?1 where u.id = ?2")
    void changeEmail(String email, long name);
}

