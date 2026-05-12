package org.example.trackertask.controller.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.trackertask.controller.TaskController;
import org.example.trackertask.dto.response.TaskResponse;
import org.example.trackertask.dto.request.TaskRequest;
import org.example.trackertask.dto.request.TaskUpdateRequest;
import org.example.trackertask.model.TaskStatus;

import org.example.trackertask.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskControllerImpl implements TaskController {
    private final TaskService taskService;

    @Override
    public ResponseEntity<Void> createTask(
            @Valid @RequestBody TaskRequest taskRequest,
            @RequestHeader("X-User-Id") Long userId
            ) {
        taskService.createTask(taskRequest, userId);

        return ResponseEntity.created(URI.create("/tasks")).build();
    }

    @Override
    public ResponseEntity<List<TaskResponse>> getAllTasksByStatus(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(required = false, name = "status") TaskStatus status
    ) {
        List<TaskResponse> tasks = taskService.getAllTasks(userId, status);

        return ResponseEntity.ok(tasks);
    }

    @Override
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) {
        taskService.deleteTask(id, userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateTask(
            @Valid @RequestBody TaskUpdateRequest taskUpdateRequest,
            @PathVariable("id") Long taskId,
            @RequestHeader(value = "X-User-Id") Long userId
    ) {
        taskService.updateTask(taskUpdateRequest,  taskId, userId);

        return ResponseEntity.noContent().build();
    }

}
