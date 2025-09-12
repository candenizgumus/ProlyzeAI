package com.prolyzeai.dto.response;

import java.util.Map;

public record DashboardGetCategoryExpensesForCurrentYearResponseDto(Map<String,Double> expenses)
{
}
