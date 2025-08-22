package com.example.eccomerce.model;

import com.example.eccomerce.model.enums.ERole;
import com.example.eccomerce.model.enums.EUserState;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.*;


@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    private String id;
    private String name;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private String password;
    private LocalDate birthday;
    private Date registerDate;
    private String address;
    private int numberPhone;
    private Set<ERole>roleList;
    private EUserState state;


}
