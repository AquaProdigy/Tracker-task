package org.example.trackertask.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(
        @NotBlank(message = "Title not be blank")
        @Size(min = 3, max = 40, message = "Title must be between {min} and {max} character long")
        String title,

        @NotBlank(message = "Description not be blank")
        @Size(min = 3, max = 255, message = "Description must be between {min} and {max} character long")
        String description
) {}
