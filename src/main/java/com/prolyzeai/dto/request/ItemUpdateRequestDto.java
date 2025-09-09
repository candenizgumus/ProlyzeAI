package com.prolyzeai.dto.request;

public record ItemUpdateRequestDto(String id, String categoryId, String description, Double unitPrice, Integer quantity)
{
}
