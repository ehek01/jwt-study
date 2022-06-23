package me.study.jwt.controller;

import me.study.jwt.dto.LoginDto;
import me.study.jwt.dto.TokenDto;
import me.study.jwt.jwt.JwtFilter;
import me.study.jwt.jwt.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController { // login api 추가
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        // authenticationToken 를 이용해서 authenticate 메소드가 실행될 때, CustomUserDetailsService 에 있는
        // loadUserByUsername 메소드가 실행되어 그 리턴값을 가지고 Authentication 객체를 생성해줌
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 생성한 authentication 객체를 SecurityContext 에 저장하고,
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Authentication 객체를 통한 인증정보를 기준으 TokenProvider 에서 만든 createToken 메소드를 통해서 JWT 토큰을 생성함.
        String jwt = tokenProvider.createToken(authentication);

        // jwt 토큰을 Response Header 에도 넣어주고,
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        // TokenDto 를 이용해서 ResponseBody 에도 넣어서 반환해줌.
        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}
