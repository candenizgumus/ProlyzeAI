package com.prolyzeai.dto.request;

import com.prolyzeai.entities.enums.EUserType;

public record AuthUpdateRequestDto(String id, String email, String password, EUserType userType)
{
}
