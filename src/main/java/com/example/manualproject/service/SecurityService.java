package com.example.manualproject.service;

import java.util.ArrayList;
import java.util.List;

public interface SecurityService {
    public List<Object> getLoggedInUsers();

    public void logout(Object principal);
}

