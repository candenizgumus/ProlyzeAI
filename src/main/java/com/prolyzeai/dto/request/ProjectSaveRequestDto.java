package com.prolyzeai.dto.request;

import java.time.LocalDate;

public record ProjectSaveRequestDto(String name, String description, Double agreedPrice, LocalDate startDate, LocalDate endDate, LocalDate plannedEndDate)
{
}
