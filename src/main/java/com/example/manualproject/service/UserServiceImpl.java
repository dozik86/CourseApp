package com.example.manualproject.service;

import com.example.manualproject.model.Role;
import com.example.manualproject.model.User;
import com.example.manualproject.repository.RoleRepository;
import com.example.manualproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Transactional
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SecurityService securityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailConfirm emailConfirm;

    public User findById(long id) {
        return userRepository.findById(id);
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findBySocialId(String socialId) {
        return userRepository.findBySocialid(socialId);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthvia("native");
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
        emailConfirm.sendMail(user.getName(), user.getEmail());    // todo email save
    }

    public void saveSocialUser(String userName, String userId, String via) {
        User user = new User();
        user.setSocialid(userId);
        user.setName(userName);
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        user.setAuthvia(via);
        userRepository.save(user);
    }

    public boolean confirm(String userName, String hash) {
        if (passwordEncoder.matches(userName, hash)) {
            userRepository.confirm(userName);
            return true;
        } else return false;
    }

    public void renameUser(String newName, long id) {
        userRepository.renameUser(newName, id);
    }

    public void changeEmail(String email, String userName, String hash) {
        if (passwordEncoder.matches(userName, hash)) {
            userRepository.changeEmail(email, userName);
        }
    }

    public void changeEmailByAdmin(String email, long userId) {
        userRepository.changeEmail(email, userId);
    }

    public void changePassword(String newPassword, long id) {
        userRepository.changePassword(passwordEncoder.encode(newPassword), id);
    }

    public void authUser(User user) {
        Authentication auth = new UsernamePasswordAuthenticationToken(new CustomUserDetails(user, getAuthorities(user)), null, getAuthorities(user));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public Set<GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return grantedAuthorities;
    }

    public void delete(String[] delArray) {
        long[] idArray = Arrays.asList(delArray).stream().mapToLong(Long::parseLong).toArray();
        for (int i = 0; i < idArray.length; i++) {
            logoutUser(idArray[i]);
            userRepository.deleteById(idArray[i]);
        }
    }

    public void block(String[] blockArray) {
        long[] idArray = Arrays.asList(blockArray).stream().mapToLong(Long::parseLong).toArray();
        for (int i = 0; i < idArray.length; i++) {
            logoutUser(idArray[i]);
            userRepository.block(idArray[i]);
        }
    }

    public void unblock(String[] unblockArray) {
        long[] idArray = Arrays.asList(unblockArray).stream().mapToLong(Long::parseLong).toArray();
        for (int i = 0; i < idArray.length; i++) {
            userRepository.unblock(idArray[i]);
        }
    }

    public void addRole(String[] roleArray, String role) {
        User user;
        long[] idArray = Arrays.asList(roleArray).stream().mapToLong(Long::parseLong).toArray();
        for (int i = 0; i < idArray.length; i++) {
            user = userRepository.findById(idArray[i]);
            Set<Role> roles = user.getRoles();
            roles.add(roleRepository.findByName(role).iterator().next());
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

    public void delRole(String[] roleArray, String role) {
        User user;
        long[] idArray = Arrays.asList(roleArray).stream().mapToLong(Long::parseLong).toArray();
        for (int i = 0; i < idArray.length; i++) {
            user = userRepository.findById(idArray[i]);
            Set<Role> roles = user.getRoles();
            roles.remove(roleRepository.findByName(role).iterator().next());
            user.setRoles(roles);
            userRepository.save(user);
        }
    }


    public void logoutUser(long userId) {
        List<Object> loggedInUsers = securityService.getLoggedInUsers();
        for (Object principal : loggedInUsers)
            if (((CustomUserDetails) principal).getId() == userId) {
                securityService.logout(principal);
                return;
            }
    }

}


