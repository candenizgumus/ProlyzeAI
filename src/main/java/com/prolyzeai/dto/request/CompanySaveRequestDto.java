package com.prolyzeai.dto.request;

import com.prolyzeai.entities.enums.ECurrency;

public record CompanySaveRequestDto(String name, String city, String address, ECurrency currency)
{
}
