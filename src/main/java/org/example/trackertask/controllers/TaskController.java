package org.example.trackertask.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.trackertask.dto.TaskDto;
import org.example.trackertask.dto.request.TaskRequest;
import org.example.trackertask.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Void> createTask(
            @Validated @RequestBody TaskRequest taskRequest,
            @RequestHeader("X-User-Id") Long userId
            ) {
        taskService.createTask(taskRequest, userId);

        return ResponseEntity.created(URI.create("/tasks")).build();
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(
            @RequestHeader("X-User-Id") Long userId
    ) {
        List<TaskDto> tasks = taskService.getAllTasks(userId);

        return ResponseEntity.ok(tasks);
    }
}
