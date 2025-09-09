package com.prolyzeai.dto.request;

import com.prolyzeai.entities.enums.ECashFlowType;

import java.time.LocalDate;

public record CashFlowUpdateRequestDto(String id,String categoryId, LocalDate date , String description, Double amount)
{
}
