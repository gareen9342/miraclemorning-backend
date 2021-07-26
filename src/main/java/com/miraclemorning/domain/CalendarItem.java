package com.miraclemorning.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name="CALENDAR_ITEM")
public class CalendarItem {
    @Id @GeneratedValue
    @Column(name="calendar_item_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="calendar_id")
    private Calendar calendar;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "calendarItem")
    private List<Image> images = new ArrayList<>();

    @Column(name="calendar_item_title")
    private String title;

    @Column(name="calendar_item_content")
    private String content;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
