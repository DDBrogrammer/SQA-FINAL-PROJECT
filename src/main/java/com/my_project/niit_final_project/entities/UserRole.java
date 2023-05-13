package com.my_project.niit_final_project.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="roles")

public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(mappedBy = "userRoles")
    Set<User> users;
    @Column(name="name")
    String name;
    @Column(name="fe_name")
    String feName;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeName() {
        return feName;
    }

    public void setFeName(String feName) {
        this.feName = feName;
    }

    public UserRole(Long id, Set<User> users, String name, String feName) {
        this.id = id;
        this.users = users;
        this.name = name;
        this.feName = feName;
    }

    public UserRole() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id) && Objects.equals(users, userRole.users) && Objects.equals(name, userRole.name) && Objects.equals(feName, userRole.feName);
    }


    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", users=" + users +
                ", name='" + name + '\'' +
                ", feName='" + feName + '\'' +
                '}';
    }
}
