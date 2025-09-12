package com.prolyzeai.dto.request;

import java.time.LocalDate;

public record ProjectUpdateRequestDto(String id, String name, String description, Double agreedPrice, LocalDate startDate, LocalDate endDate, Boolean isCompleted , LocalDate plannedEndDate)
{
}
