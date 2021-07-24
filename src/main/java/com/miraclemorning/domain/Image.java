package com.miraclemorning.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name="IMAGE")
public class Image {
    @Id @GeneratedValue
    @Column(name="image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="calendar_item_id")
    private CalendarItem calendarItem;

    private String url;

    private String alt;
}
