//package me.study.jwt.config;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@EnableWebSecurity // 기본적인 웹보안을 활성화 하겠다.
//public class SecurityConfig extends WebSecurityConfigurerAdapter { // 추가적인 설정을 위해서 WebSecurityConfigurer 를 implements 하거나,
//                                // WebSecurityConfigurerAdapter 를 extends 하는 방법이 있다.
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests() // HttpServletRequest 를 사용하는 요청들에 대한 접근제한 설정.
//                .antMatchers("/api/hello").permitAll() // 인증없이 해당주소 접근을 허용.
//                .anyRequest().authenticated(); // 그리고, 나머지 요청들에 대해서는 모두 인증을 거쳐야 한다.
//    }
//}
