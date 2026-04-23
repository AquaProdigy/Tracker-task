package org.example.trackertask.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.trackertask.dto.TaskDto;
import org.example.trackertask.dto.request.TaskRequest;
import org.example.trackertask.dto.request.TaskUpdateRequest;
import org.example.trackertask.enums.TaskStatus;
import org.example.trackertask.services.TaskService;
import org.springframework.http.ResponseEntity;
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
            @Valid @RequestBody TaskRequest taskRequest,
            @RequestHeader("X-User-Id") Long userId
            ) {
        taskService.createTask(taskRequest, userId);

        return ResponseEntity.created(URI.create("/tasks")).build();
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasksByStatus(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(required = false, name = "status") TaskStatus status
    ) {
        List<TaskDto> tasks = taskService.getAllTasks(userId, status);

        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) {
        taskService.deleteTask(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTask(
            @Valid @RequestBody TaskUpdateRequest taskUpdateRequest,
            @PathVariable("id") Long taskId,
            @RequestHeader(value = "X-User-Id") Long userId
    ) {
        taskService.updateTask(taskUpdateRequest,  taskId, userId);

        return ResponseEntity.noContent().build();
    }

}
