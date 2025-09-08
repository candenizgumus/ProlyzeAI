package com.prolyzeai.controller;


import com.prolyzeai.dto.request.AuthLoginRequestDto;
import com.prolyzeai.dto.response.AuthLoginResponseDto;
import com.prolyzeai.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.prolyzeai.constants.Endpoints.*;


@RestController
@RequestMapping(ROOT + AUTH)
@RequiredArgsConstructor
public class AuthController
{

    private final AuthService authService;


    @PostMapping(LOGIN)
    public ResponseEntity<AuthLoginResponseDto> login(@RequestBody AuthLoginRequestDto dto)
    {
        return ResponseEntity.ok(authService.login(dto));
    }




}
