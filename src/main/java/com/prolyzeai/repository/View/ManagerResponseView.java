package com.prolyzeai.repository.View;


import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public interface ManagerResponseView
{
    UUID getId();
    @Value("#{target.company.id}")
    String getCompanyId();
    String getName();
    String getSurname();
    String getPhoneNumber();
    @Value("#{target.company.selectedCurrency}")
    String getSelectedCurrency();
    @Value("#{target.auth.email}")
    String getEmail();
}
