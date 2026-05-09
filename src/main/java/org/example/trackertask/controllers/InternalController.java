package org.example.trackertask.controllers;


import lombok.RequiredArgsConstructor;

import org.example.trackertask.dto.UserDailyTaskSummary;
import org.example.trackertask.services.InternalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/tasks/daily-summary")
public class InternalController {
    private final InternalService internalService;

    @GetMapping
    public ResponseEntity<List<UserDailyTaskSummary>> getDailySummery() {
        return ResponseEntity.ok(internalService.getSummaryTasks());
    }
}
