package me.study.jwt.config;

import me.study.jwt.jwt.JwtAccessDeniedHandler;
import me.study.jwt.jwt.JwtAuthenticationEntryPoint;
import me.study.jwt.jwt.JwtSecurityConfig;
import me.study.jwt.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 나중에 @PreAuthorize 어노테이션을 메소드 단위로 추가하기 위해 적용.
public class SecurityConfiguration {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfiguration(TokenProvider tokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // password Encoding 방식
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web
                .ignoring()
                .antMatchers("/h2-console/**","/favicon.ico"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 토큰 방식을 사용하기 때문에 csrf 를 꺼줌.

                // 커스터마이징한 엔트리포인트와 핸들러를 추가해줌.
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // h2 console 을 위한 설정.
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않기 때문에 세션 설정을 STATELESS 로 함.
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests() // HttpServletRequest 를 사용하는 요청들에 대한 접근제한 설정.
                .antMatchers("/api/hello").permitAll() // 인증없이 해당주소 접근을 허용.
                .antMatchers("/api/authenticate").permitAll()  // 로그인 api, 회원가입 api 는 토큰이 없는 상태에서 요청이 들어오기 때문에 접근을 허용해줌.
                .antMatchers("/api/signup").permitAll() // 로그인 api, 회원가입 api 는 토큰이 없는 상태에서 요청이 들어오기 때문에 접근을 허용해줌.
                .anyRequest().authenticated() // 그리고, 나머지 요청들에 대해서는 모두 인증을 거쳐야 한다.

                .and()
                .apply(new JwtSecurityConfig(tokenProvider)); // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스도 적용

        return http.build();
    }
}
