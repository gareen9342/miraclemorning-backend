package com.miraclemorning.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter @ToString
@Entity @Table(name = "MEMBER_AUTH")
public class MemberAuth {
    /**
     * GenerationType
     * 1. IDENTITY : 데이터베이스에 위임 (Auto increment)
     * 2. Increment : 데이터베이스 시퀀스 오브젝트 사용 (ORACLE) -> @SequenceGenerator 필요
     * 3. TABLE : 키 생성용 테이블 사용, 모든 DB에서 사용
     * 4. AUTO : 방언에 따라 자동 지정, 기본값     *
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_auth_id")
    private Long id;

    @Column(name="member_id")
    private Long memberId;

    @Column(length = 50)
    private String auth;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
