package com.example.manualproject.repository;

import com.example.manualproject.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Set;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findByName(String name);
}
