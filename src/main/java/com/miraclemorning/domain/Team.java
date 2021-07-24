package com.miraclemorning.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Team {

    @Id @GeneratedValue
    @Column(name="team_id")
    private Long id;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    @Column(name="team_name")
    private String name;

    @Column(name="team_desc")
    private String desc;

    @OneToOne(fetch = LAZY, mappedBy = "team")
    private Calendar calendar;
}
