package me.study.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests() // HttpServletRequest 를 사용하는 요청들에 대한 접근제한 설정.
                .antMatchers("/api/hello").permitAll() // 인증없이 해당주소 접근을 허용.
                .anyRequest().authenticated(); // 그리고, 나머지 요청들에 대해서는 모두 인증을 거쳐야 한다.

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web
                .ignoring()
                .antMatchers("/h2-console/**","/favicon.ico"));
    }
}
