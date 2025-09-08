package com.prolyzeai.dto.response;

import com.prolyzeai.entities.enums.EUserType;

public record AuthLoginResponseDto(String token, EUserType userType)
{
}
