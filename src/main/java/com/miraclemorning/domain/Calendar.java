package com.miraclemorning.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name="CALENDAR")
public class Calendar {
    @Id @GeneratedValue
    @Column(name = "calendar_id")
    private Long id;

    @Column(name="calendar_title")
    private String title;

    @Column(name="calendar_desc")
    private String desc;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="team_id")
    private Team team;

    @OneToMany(mappedBy = "calendar")
    private List<CalendarItem> calendarItems = new ArrayList<>();


}
