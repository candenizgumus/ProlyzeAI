package com.prolyzeai.dto.request;

import com.prolyzeai.entities.enums.ECurrency;

public record CompanyUpdateRequestDto(String id, String name, String city, String address, ECurrency currency)
{
}
