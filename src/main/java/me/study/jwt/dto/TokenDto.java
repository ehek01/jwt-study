package me.study.jwt.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto { // 토큰정보를 response 할때 사용할 Dto
    private String token;
}
