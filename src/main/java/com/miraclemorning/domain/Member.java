package com.miraclemorning.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
@Entity @Table(name="MEMBER")
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_password")
    private String password;

    @Column(name = "member_name")
    private String name;

    private String avatar;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "update_date")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team team;

}
