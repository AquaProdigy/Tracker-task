package org.example.trackertask.dto;

import java.util.List;

public record UserDailyTaskSummary(
        Long userId,
        List<TaskSummaryDto> completedTasks,
        List<TaskSummaryDto> inProgressTasks
) {}
