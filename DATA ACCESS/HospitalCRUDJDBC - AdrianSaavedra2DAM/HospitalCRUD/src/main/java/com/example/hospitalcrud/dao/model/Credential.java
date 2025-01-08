package com.example.hospitalcrud.dao.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter@Setter
public class Credential {

    private String userName;
    private String password;
    private Integer id;


    public Credential(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


}
