package org.example.trackertask.dto;

import org.example.trackertask.enums.TaskStatus;

import java.time.LocalDateTime;

public record TaskDto(
        String title,
        String description,
        TaskStatus status,
        LocalDateTime created
) {}
