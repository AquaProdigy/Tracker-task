package org.example.trackertask.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.trackertask.model.TaskStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "tasks",
        uniqueConstraints = @UniqueConstraint(name = "uq_tasks_user_title", columnNames = {"user_id", "title"}))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status =  TaskStatus.IN_PROCESS;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;
}
