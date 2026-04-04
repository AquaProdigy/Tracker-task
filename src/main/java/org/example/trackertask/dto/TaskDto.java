package org.example.trackertask.dto;

import org.example.trackertask.enums.TaskStatus;

import java.time.LocalDateTime;

public record TaskDto(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime createdAt
) {}
