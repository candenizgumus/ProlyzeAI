package com.prolyzeai.service;


import com.prolyzeai.dto.request.AuthChangePasswordRequestDto;
import com.prolyzeai.dto.request.AuthLoginRequestDto;
import com.prolyzeai.dto.request.AuthSaveRequestDto;
import com.prolyzeai.dto.request.AuthUpdateRequestDto;
import com.prolyzeai.dto.response.AuthLoginResponseDto;
import com.prolyzeai.entities.Auth;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.entities.enums.EUserType;
import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import com.prolyzeai.repository.AuthRepository;
import com.prolyzeai.utils.JwtTokenManager;
import com.prolyzeai.utils.PasswordEncoder;
import com.prolyzeai.utils.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static com.prolyzeai.utils.UtilMethods.checkEmailFormat;


@Service
@RequiredArgsConstructor
public class AuthService
{
    private final AuthRepository authRepository;
    private final JwtTokenManager jwtTokenManager;

    public Auth save(AuthSaveRequestDto dto)
    {
        if (authRepository.existsByEmail(dto.email()))
        {
            throw new ProlyzeException(ErrorType.EMAIL_TAKEN);
        }
        //Checks email format
        checkEmailFormat(dto.email());

        return authRepository.save(Auth
                .builder()
                .email(dto.email())
                .password(PasswordEncoder.bCryptPasswordEncoder().encode(dto.password()))
                .userType(dto.userType())
                .build());
    }

    public Auth update(AuthUpdateRequestDto dto)
    {

        Auth auth = authRepository.findById(UUID.fromString(dto.id())).orElseThrow(() -> new ProlyzeException(ErrorType.AUTH_NOT_FOUND));

        //Checks email format
        checkEmailFormat(dto.email());

        //Checks if email is changed
        if (!dto.email().equals(auth.getEmail()))
        {
            if (authRepository.existsByEmail(dto.email()))
            {
                throw new ProlyzeException(ErrorType.EMAIL_TAKEN);
            }
        }


        auth.setEmail(dto.email());
        auth.setPassword(PasswordEncoder.bCryptPasswordEncoder().encode(dto.password()));
        auth.setUserType(dto.userType());
        return authRepository.save(auth);

    }

    public Boolean delete(String uuid) {
        Auth auth = authRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> new ProlyzeException(ErrorType.AUTH_NOT_FOUND));
        auth.setStatus(EStatus.DELETED);
        authRepository.save(auth);
        return true;
    }

    public Auth findById(UUID id)
    {
        return authRepository.findById(id).orElseThrow(() -> new ProlyzeException(ErrorType.AUTH_NOT_FOUND));
    }

    public Boolean existsByEmail(String email)
    {
        return authRepository.existsByEmail(email);
    }

    public AuthLoginResponseDto login(AuthLoginRequestDto dto) {
        Auth auth = authRepository.findByEmail(dto.email()).orElseThrow(() -> new ProlyzeException(ErrorType.EMAIL_OR_PASSWORD_WRONG));

        //Password Check
        if (!PasswordEncoder.bCryptPasswordEncoder().matches(dto.password(), auth.getPassword())) {
            throw new ProlyzeException(ErrorType.EMAIL_OR_PASSWORD_WRONG);
        }
        //Subscription Check. Only checks for managers.
        if (auth.getSubscriptionEndDate().isBefore(LocalDate.now()) && auth.getUserType().equals(EUserType.MANAGER))
        {
            throw new ProlyzeException(ErrorType.SUBSCRIPTION_EXPIRED);
        }
        //Status Checks
        if (!auth.getStatus().equals(EStatus.ACTIVE))
        {
            throw new ProlyzeException(ErrorType.ACCOUNT_STATUS_ERROR, "Hesap durumu: " + auth.getStatus().name());
        }

        var accessToken = jwtTokenManager.createAccessToken(auth.getId().toString())
                .orElseThrow(() -> new ProlyzeException(ErrorType.TOKEN_CREATION_FAILED));

        return new AuthLoginResponseDto(accessToken, auth.getUserType());
    }

    public Integer getRemainingSubscriptionTime() {
        Auth auth = SessionManager.getAuthFromToken();
        LocalDate now = LocalDate.now();
        LocalDate endDate = auth.getSubscriptionEndDate();

        return (int) ChronoUnit.DAYS.between(now, endDate);
    }

    public Boolean changePassword(AuthChangePasswordRequestDto dto)
    {
        Auth auth = SessionManager.getAuthFromToken();

        if (!PasswordEncoder.bCryptPasswordEncoder().matches(dto.oldPassword(), auth.getPassword())) {
            throw new ProlyzeException(ErrorType.INVALID_PASSWORD_OR_EMAIL);
        }

        auth.setPassword(PasswordEncoder.bCryptPasswordEncoder().encode(dto.newPassword()));
        authRepository.save(auth);

        return null;
    }

    /**
     * All the users in the clinic will be updated with the new subscription date
     * @param dto
     * @return boolean
     *//*
    public Boolean addSubscription(AuthAddSubscriptionDto dto) {
        List<Manager> managers = managerService.findAllByClinic_Id(UUID.fromString(dto.clinicId()));
        List<Patient> patients = patientService.findAllByClinic_Id(UUID.fromString(dto.clinicId()));

        //If the current date is past the subscription end date, the new period starts from today instead of extending the expired date.
        managers.forEach(manager -> {
            Auth auth = manager.getAuth();
            auth.setSubscriptionEndDate(calculateNewSubscriptionDate(auth.getSubscriptionEndDate(), dto.month()));
        });

        patients.forEach(patient -> {
            Auth auth = patient.getAuth();
            auth.setSubscriptionEndDate(calculateNewSubscriptionDate(auth.getSubscriptionEndDate(), dto.month()));
        });

        managerService.saveAll(managers);
        patientService.saveAll(patients);
        return true;
    }

    private LocalDate calculateNewSubscriptionDate(LocalDate currentEndDate, int monthsToAdd) {
        LocalDate now = LocalDate.now();
        return currentEndDate.isBefore(now)
                ? now.plusMonths(monthsToAdd)
                : currentEndDate.plusMonths(monthsToAdd);
    }*/


}
