package com.prolyzeai.repository.View;


import com.prolyzeai.entities.enums.ECurrency;

import java.util.UUID;

public interface CompanyResponseView
{
    UUID getId();
    String getName();
    String getCity();
    String getAddress();
    ECurrency getSelectedCurrency();
}
