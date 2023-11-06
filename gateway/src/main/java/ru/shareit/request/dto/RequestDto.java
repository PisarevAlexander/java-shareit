package ru.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * The type Request dto
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {

    private Long id;

    @NotBlank(message = "description can't be blank")
    private String description;

    private LocalDateTime created;
}
