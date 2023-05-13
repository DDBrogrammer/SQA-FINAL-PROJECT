package com.my_project.niit_final_project.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="banners")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "picture")
    String picture;
    @Column(name="status")
    int order;

}
