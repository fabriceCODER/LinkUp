package com.fabish.LinkUpAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewRequestDTO {
    private LocalDateTime dateTime;
    private String location;
}
