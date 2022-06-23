package me.study.jwt.repository;

import me.study.jwt.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities") // 쿼리가 수행될때 Lazy 조회가 아닌 Eager 조회를 함으로써,authorities 정보를 같이 가져옴.
    Optional<User> findOneWithAuthoritiesByUsername(String name); // username 으로 user 정보를 가져올때, 권한정보도 같이 가져옴.
}
