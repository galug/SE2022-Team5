package com.example.demo.src.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginUserInfo {
    private int userIdx;
    private String name;
    private String id;
    private String pwd;
}
