package com.miraclemorning.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_name")
    private String userName;

    private String avatar;

    private String role;

    @Column(name = "reg_date")
    private Date regDate;

    @Column(name = "update_date")
    private Date updateDate;

}
