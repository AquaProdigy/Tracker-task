package org.example.trackertask.dto.response;

import java.util.List;

public record UserDailyTaskSummaryResponse(
        Long userId,
        List<TaskSummaryResponse> completedTasks,
        List<TaskSummaryResponse> inProgressTasks
) {}
