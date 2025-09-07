package com.prolyzeai.service;


import com.prolyzeai.dto.request.AuthSaveRequestDto;
import com.prolyzeai.entities.Auth;
import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import com.prolyzeai.repository.AuthRepository;
import com.prolyzeai.utils.JwtTokenManager;
import com.prolyzeai.utils.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.prolyzeai.utils.UtilMethods.checkEmailFormat;


@Service
@RequiredArgsConstructor
public class AuthService
{
    private final AuthRepository authRepository;
    private final JwtTokenManager jwtTokenManager;

    public Auth save(AuthSaveRequestDto dto) {
        if (authRepository.existsByEmail(dto.email())) {
            throw new ProlyzeException(ErrorType.EMAIL_TAKEN);
        }
        //Checks email format
        checkEmailFormat(dto.email());

        return authRepository.save(Auth
                .builder()
                .email(dto.email())
                .password(PasswordEncoder.bCryptPasswordEncoder().encode(dto.password()))
                .build());
    }

    public Auth findById(UUID id) {
        return authRepository.findById(id).orElseThrow(() -> new ProlyzeException(ErrorType.AUTH_NOT_FOUND));
    }



}
