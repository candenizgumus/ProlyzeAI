package com.prolyzeai.service;


import com.prolyzeai.dto.request.AdminSaveRequestDto;
import com.prolyzeai.dto.request.AdminUpdateRequestDto;
import com.prolyzeai.dto.request.AuthSaveRequestDto;
import com.prolyzeai.dto.request.PageRequestDto;
import com.prolyzeai.repository.View.AdminResponseView;
import com.prolyzeai.entities.Admin;
import com.prolyzeai.entities.Auth;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.entities.enums.EUserType;
import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import com.prolyzeai.repository.AdminRepository;
import com.prolyzeai.utils.PasswordEncoder;
import com.prolyzeai.utils.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


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

    public Boolean delete(String uuid)
    {
        Admin admin = adminRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> new ProlyzeException(ErrorType.USER_NOT_FOUND));
        admin.getAuth().setStatus(EStatus.DELETED);
        adminRepository.save(admin);
        return true;
    }

    public Admin update(AdminUpdateRequestDto dto)
    {

        Admin admin = adminRepository.findById(UUID.fromString(dto.id())).orElseThrow(() -> new ProlyzeException(ErrorType.USER_NOT_FOUND));
        if (!admin.getAuth().getEmail().equals(dto.email())) {
            if (authService.existsByEmail(dto.email()))
            {
                throw new ProlyzeException(ErrorType.EMAIL_TAKEN);
            }
        }
        admin.getAuth().setEmail(dto.email());
        admin.setName(dto.name());
        admin.setSurname(dto.surname());
        admin.getAuth().setPassword(PasswordEncoder.bCryptPasswordEncoder().encode(dto.password()));
        return adminRepository.save(admin);
    }

    public List<AdminResponseView> findAll(PageRequestDto dto)
    {
        return adminRepository.findAllByNameContainingIgnoreCaseAndAuth_StatusIsNotOrderByNameAsc(dto.searchText(), EStatus.DELETED, PageRequest.of(dto.page(), dto.pageSize()));
    }

    public AdminResponseView findViewById(String uuid)
    {
        return adminRepository.findViewById(UUID.fromString(uuid)).orElseThrow(() -> new ProlyzeException(ErrorType.USER_NOT_FOUND));
    }

    public AdminResponseView findByToken()
    {
        Auth adminAuth = SessionManager.getAuthFromToken();
        return adminRepository.findByAuth(adminAuth).orElseThrow(() -> new ProlyzeException(ErrorType.USER_NOT_FOUND));
    }
}
