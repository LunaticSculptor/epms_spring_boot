package com.example.epms.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UserLogin {

    private String email;
    private String password;
}
