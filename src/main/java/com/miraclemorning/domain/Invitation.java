package com.miraclemorning.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter @Setter
@Entity
public class Invitation {
    @Id @GeneratedValue
    @Column(name = "invitation_id")
    private Long id;

    @Column(name = "invitor_id")
    private Long invitorId;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "invitee_email")
    private String email;

    private String token;

    private boolean used;
}
