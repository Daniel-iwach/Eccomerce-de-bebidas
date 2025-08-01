package com.example.eccomerce.model;

import com.example.eccomerce.model.enums.ERole;
import com.example.eccomerce.model.enums.EUserState;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.*;

import java.util.*;


@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private String id;
    private String name;
    private String email;
    private String password;
    private Date registerDate;
    private String address;
    private int numberPhone;
    private Set<ERole>roleList;
    private EUserState state;

    @PrePersist
    private void userInicializer(){
        this.state=EUserState.DESACTIVADO;
        this.roleList=new LinkedHashSet<>();
        this.roleList.add(ERole.USER);
        this.registerDate=new Date();
    }



}
