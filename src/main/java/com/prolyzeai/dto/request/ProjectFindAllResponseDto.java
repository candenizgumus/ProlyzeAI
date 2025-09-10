package com.prolyzeai.dto.request;

import java.time.LocalDate;

public record ProjectFindAllResponseDto(String id,
                                        String name,
                                        String description,
                                        Double agreedPrice,
                                        LocalDate startDate,
                                        LocalDate endDate,
                                        Boolean isCompleted,
                                        Double profitability)
{
}
