package org.example.trackertask.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiMessages {
    TASK_ALREADY_EXISTS("Task already exists"),
    TASK_NOT_FOUND("Task not found"),;

    public final String message;
}
