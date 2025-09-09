package com.prolyzeai.repository.View;


import com.prolyzeai.entities.enums.ECashFlowType;
import com.prolyzeai.entities.enums.ECurrency;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.UUID;

public interface CashFlowResponseView
{
    UUID getId();
    LocalDate getDate();
    @Value("#{target.category.name}")
    String getCategoryName();
    String getDescription();
    Double getAmount();
    ECashFlowType getCashFlowType();
}
