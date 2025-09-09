package com.prolyzeai.repository.View;


import com.prolyzeai.entities.enums.ECurrency;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public interface ItemResponseView
{
    UUID getId();
    String getDescription();
    Double getUnitPrice();
    Integer getQuantity();
    Double getTotalPrice();

    @Value("#{target.category.name}")
    String getCategoryName();
}
