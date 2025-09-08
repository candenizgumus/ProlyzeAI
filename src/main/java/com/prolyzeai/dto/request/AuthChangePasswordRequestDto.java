package com.prolyzeai.dto.request;

public record AuthChangePasswordRequestDto(String oldPassword, String newPassword)
{
}
