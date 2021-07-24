package com.miraclemorning.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="TO_DO_LIST")
@Getter @Setter
public class ToDoList {
    @Id @GeneratedValue
    @Column(name="todo_id")
    private Long id;

    @Column(name="todo_title")
    private String todoTitle;

    @Column(name="todo_content")
    private String todoContent;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="team_id")
    private Team team;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "todolist")
    private List<ToDoListItem> toDoListItems = new ArrayList<>();

}
