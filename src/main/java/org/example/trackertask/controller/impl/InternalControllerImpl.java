package org.example.trackertask.controller.impl;


import lombok.RequiredArgsConstructor;

import org.example.trackertask.controller.InternalController;
import org.example.trackertask.dto.response.UserDailyTaskSummaryResponse;
import org.example.trackertask.service.InternalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/tasks/daily-summary")
public class InternalControllerImpl implements InternalController {
    private final InternalService internalService;

    @Override
    public ResponseEntity<List<UserDailyTaskSummaryResponse>> getDailySummery() {
        return ResponseEntity.ok(internalService.getSummaryTasks());
    }
}
