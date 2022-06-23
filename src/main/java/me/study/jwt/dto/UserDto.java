package me.study.jwt.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto { // 회원가입시 사용할 Dto

    @NonNull
    @Size(min = 3, max = 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NonNull
    @Size(min = 3, max = 100)
    private String password;

    @NonNull
    @Size(min = 3, max = 50)
    private String nickname;
}
