package com.example.manualproject.model;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Indexed
public class User {
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @Field
    private String name;
    private String email;
    @Column(nullable = false, columnDefinition = "boolean default 0")
    private Boolean confirmed = false;
    private String password;
    private String authvia;
    private String socialid;
    @Column(nullable = false, columnDefinition = "boolean default 0")
    private Boolean blocked = false;
    @ManyToMany(cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "author")
    @IndexedEmbedded(depth = 1)
    private Set<Workbook> workbooks;

    @OneToMany(mappedBy = "author")
    @IndexedEmbedded(depth = 1)
    private List<Comment> comments;

    @OneToMany(mappedBy = "author")
    private Set<Rating> ratings;


    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthvia() {
        return authvia;
    }

    public void setAuthvia(String authvia) {
        this.authvia = authvia;
    }

    public String getSocialid() {
        return socialid;
    }

    public void setSocialid(String socialid) {
        this.socialid = socialid;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isAdmin() {
        for (Role role : roles) {
            if (role.getName().equals("ROLE_ADMIN")) return true;
        }
        return false;
    }

    public Set<Workbook> getWorkbooks() {
        return workbooks;
    }

    public void setWorkbooks(Set<Workbook> workbooks) {
        this.workbooks = workbooks;
    }

    public int getWorkbooksQuantity() {
        return getWorkbooks().size();
    }
}
