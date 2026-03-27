package org.example.trackertask.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.trackertask.enums.TaskStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private TaskStatus status =  TaskStatus.COMPLETED;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
