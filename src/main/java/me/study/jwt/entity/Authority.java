package me.study.jwt.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authority")
public class Authority {
    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;
}