package com.prolyzeai.dto.request;

import com.prolyzeai.entities.enums.EUserType;

public record AuthSaveRequestDto(String email, String password, EUserType userType)
{
}
