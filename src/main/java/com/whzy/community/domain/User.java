package com.whzy.community.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String accessId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModify;
}
