package com.prolyzeai.dto.request;

import com.prolyzeai.entities.enums.EUserType;

public record AuthLoginRequestDto( String email, String password)
{
}
