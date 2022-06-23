package me.study.jwt.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto { // Login 시 사용할 Dto

    @NonNull
    @Size(min = 3, max = 50)
    private String username;

    @NonNull
    @Size(min = 3, max = 100)
    private String password;
}
