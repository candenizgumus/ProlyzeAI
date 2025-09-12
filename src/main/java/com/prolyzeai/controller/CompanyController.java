package com.prolyzeai.controller;


import com.prolyzeai.dto.request.CompanySetMonthlyProjectLimitRequestDto;
import com.prolyzeai.dto.request.CompanyUpdateRequestDto;
import com.prolyzeai.repository.View.CompanyResponseView;
import com.prolyzeai.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.prolyzeai.constants.Endpoints.*;


@RestController
@RequestMapping(ROOT + COMPANY)
@RequiredArgsConstructor
public class CompanyController
{

    private final CompanyService companyService;


    @PutMapping(UPDATE)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<Boolean> update(@RequestBody CompanyUpdateRequestDto dto)
    {
        companyService.update(dto);
        return ResponseEntity.ok(true);
    }


    @GetMapping(FIND_BY_ID)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<CompanyResponseView> findViewById(String id)
    {

        return ResponseEntity.ok(companyService.findViewById(id));
    }

    @PostMapping(SET_MONTHLY_PROJECT_LIMIT)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Boolean> setMonthlyProjectLimit(@RequestBody CompanySetMonthlyProjectLimitRequestDto dto)
    {
        return ResponseEntity.ok(companyService.setMonthlyProjectLimit(dto));
    }


}
