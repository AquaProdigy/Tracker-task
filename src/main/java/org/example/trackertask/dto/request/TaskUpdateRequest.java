package org.example.trackertask.dto.request;

import jakarta.validation.constraints.Size;
import org.example.trackertask.enums.TaskStatus;

public record TaskUpdateRequest(
        @Size(min = 3, max = 40, message = "Title must be between {min} and {max} character long")
        String title,

        @Size(min = 3, max = 255, message = "Description must be between {min} and {max} character long")
        String description,

        TaskStatus status
) {}
