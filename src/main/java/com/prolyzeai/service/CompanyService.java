package com.prolyzeai.service;


import com.prolyzeai.dto.request.CompanySaveRequestDto;
import com.prolyzeai.dto.request.CompanyUpdateRequestDto;
import com.prolyzeai.entities.Company;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import com.prolyzeai.repository.CategoryRepository;
import com.prolyzeai.repository.CompanyRepository;
import com.prolyzeai.repository.View.CompanyResponseView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CompanyService
{
    private final CompanyRepository categoryRepository;


    public Company save(CompanySaveRequestDto dto)
    {
        return categoryRepository.save(Company.builder()
                .name(dto.name())
                .city(dto.city())
                .address(dto.address())
                .selectedCurrency(dto.currency())
                .build());
    }
    public Company update(CompanyUpdateRequestDto dto)
    {
        Company company = categoryRepository.findById(UUID.fromString(dto.id())).orElseThrow(() -> new ProlyzeException(ErrorType.COMPANY_NOT_FOUND));
        company.setName(dto.name());
        company.setCity(dto.city());
        company.setAddress(dto.address());
        company.setSelectedCurrency(dto.currency());
        return company;
    }

    public Boolean delete(String id)
    {
        Company company = categoryRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ProlyzeException(ErrorType.COMPANY_NOT_FOUND));
        company.setStatus(EStatus.DELETED);
        categoryRepository.save(company);
        return true;
    }

    public CompanyResponseView findViewById(String id)
    {
        return categoryRepository.findViewById(UUID.fromString(id)).orElseThrow(() -> new ProlyzeException(ErrorType.COMPANY_NOT_FOUND));

    }
}
