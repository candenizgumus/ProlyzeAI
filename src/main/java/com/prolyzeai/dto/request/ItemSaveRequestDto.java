package com.prolyzeai.dto.request;

import com.prolyzeai.entities.enums.ECashFlowType;

import java.time.LocalDate;

public record ItemSaveRequestDto(String categoryId, String projectId , String description, Double unitPrice, Integer quantity)
{
}
