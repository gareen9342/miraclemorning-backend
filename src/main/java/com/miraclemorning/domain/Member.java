package com.miraclemorning.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Table( uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Email
    private String email;

    @Column(nullable = false)
    private boolean emailVerified = false;

    @JsonIgnore
    @Column(name = "member_password")
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @Column(name = "member_name")
    private String name;

    private String avatar;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team team;

    @OneToMany(mappedBy = "member")
    private List<CalendarItem> calendarItems = new ArrayList<>();
}
