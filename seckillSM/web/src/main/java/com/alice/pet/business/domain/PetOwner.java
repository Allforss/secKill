package com.alice.pet.business.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PetOwner {
    private Integer id;

    private String name;

    private Boolean sex;

    private Integer age;

    private String address;

    private String mail;

    private String identitycard;

    private Date createtime;

    private Date updatetime;
}