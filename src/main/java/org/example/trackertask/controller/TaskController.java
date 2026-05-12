package org.example.trackertask.controller;

import jakarta.validation.Valid;
import org.example.trackertask.dto.request.TaskRequest;
import org.example.trackertask.dto.request.TaskUpdateRequest;
import org.example.trackertask.dto.response.TaskResponse;
import org.example.trackertask.model.TaskStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface TaskController {
    @PostMapping
    ResponseEntity<Void> createTask(
            @Valid @RequestBody TaskRequest taskRequest,
            @RequestHeader("X-User-Id") Long userId
    );

    @GetMapping
    ResponseEntity<List<TaskResponse>> getAllTasksByStatus(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(required = false, name = "status") TaskStatus status
    );

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    );

    @PutMapping("/{id}")
    ResponseEntity<Void> updateTask(
            @Valid @RequestBody TaskUpdateRequest taskUpdateRequest,
            @PathVariable("id") Long taskId,
            @RequestHeader(value = "X-User-Id") Long userId
    );
}
