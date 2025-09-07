package com.prolyzeai.controller;


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


    /*@PostMapping(LOGIN)
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto dto) {
        AuthResponseDto authResponseDto = authService.login(dto);
        return ResponseEntity.ok(authResponseDto);
    }


    @PostMapping(LOGOUT)
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','PATIENT')")
    public ResponseEntity<Boolean> logout() {

        return ResponseEntity.ok(authService.logout());
    }*/




}
