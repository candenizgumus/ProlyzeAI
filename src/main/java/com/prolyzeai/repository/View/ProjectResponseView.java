package com.prolyzeai.repository.View;


import com.prolyzeai.entities.enums.ECurrency;

import java.time.LocalDate;
import java.util.UUID;

public interface ProjectResponseView
{
    UUID getId();
    String getName();
    String getDescription();
    Double getAgreedPrice();
    LocalDate getStartDate();
    LocalDate getEndDate();
    LocalDate getPlannedEndDate();
    Boolean getIsCompleted();
}
