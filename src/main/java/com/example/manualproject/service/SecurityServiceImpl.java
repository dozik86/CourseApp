package com.example.manualproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityServiceImpl implements SecurityService {
    @Resource(name = "sessionRegistry")
    private SessionRegistry sessionRegistry;


    public List<Object> getLoggedInUsers() {
        List<Object> principals = sessionRegistry.getAllPrincipals();
        return principals;
    }

    public void logout(Object principal) {
        List<SessionInformation> sessionInformation = sessionRegistry.getAllSessions(principal, true);
        for (SessionInformation el : sessionInformation) {
            el.expireNow();
        }
    }
}
