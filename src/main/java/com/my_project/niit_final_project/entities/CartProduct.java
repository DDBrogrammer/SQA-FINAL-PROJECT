package com.my_project.niit_final_project.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public  class CartProduct implements Serializable {
    long id;
    String name;
    Double price;
    int quantity;
    String picture;
}
