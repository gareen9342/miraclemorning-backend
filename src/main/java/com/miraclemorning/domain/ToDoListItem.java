package com.miraclemorning.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="TO_DO_LIST_ITEM")
@Getter @Setter
public class ToDoListItem {
    @Id @GeneratedValue
    @Column(name="todo_item_id")
    private Long id;

    @Column(name="todo_item_content")
    private String content;

    private int checked;

    private int deleted;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="todo_id")
    private ToDoList todolist;
}
