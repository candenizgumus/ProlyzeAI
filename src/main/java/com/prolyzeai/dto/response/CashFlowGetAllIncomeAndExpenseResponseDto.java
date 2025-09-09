package com.prolyzeai.dto.response;

import com.prolyzeai.entities.enums.EUserType;

public record CashFlowGetAllIncomeAndExpenseResponseDto(Double income, Double expense, Double balance)
{
}
