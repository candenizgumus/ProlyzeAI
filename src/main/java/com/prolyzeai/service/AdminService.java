package com.prolyzeai.service;


import com.prolyzeai.dto.request.AdminSaveRequestDto;
import com.prolyzeai.dto.request.AuthSaveRequestDto;
import com.prolyzeai.entities.Admin;
import com.prolyzeai.entities.Auth;
import com.prolyzeai.entities.enums.EUserType;
import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import com.prolyzeai.repository.AdminRepository;
import com.prolyzeai.utils.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.prolyzeai.utils.UtilMethods.checkEmailFormat;


@Service
@RequiredArgsConstructor
public class AdminService
{
    private final AdminRepository adminRepository;
    private final AuthService authService;


    public Admin save(AdminSaveRequestDto dto)
    {
        Auth auth = authService.save(new AuthSaveRequestDto(dto.email(), dto.password(), EUserType.ADMIN));

        return adminRepository.save(Admin.builder()
                .auth(auth)
                .name(dto.name())
                .surname(dto.surname())
                .build());
    }
}
