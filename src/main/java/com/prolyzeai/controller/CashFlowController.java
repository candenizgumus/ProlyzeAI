package com.prolyzeai.controller;


import com.prolyzeai.dto.request.*;
import com.prolyzeai.dto.response.CashFlowGetAllIncomeAndExpenseResponseDto;
import com.prolyzeai.repository.View.CashFlowResponseView;
import com.prolyzeai.repository.View.CategoryResponseView;
import com.prolyzeai.service.CashFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.prolyzeai.constants.Endpoints.*;


@RestController
@RequestMapping(ROOT + CASH_FLOW)
@RequiredArgsConstructor
public class CashFlowController
{

    private final CashFlowService cashFlowService;

    @PostMapping(SAVE)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<Boolean> save(@RequestBody CashFlowSaveRequestDto dto){

        cashFlowService.save(dto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping(DELETE)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<Boolean> delete(String id){

        return ResponseEntity.ok(cashFlowService.delete(id));
    }

    @PutMapping(UPDATE)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<Boolean> update(@RequestBody CashFlowUpdateRequestDto dto){

        cashFlowService.update(dto);
        return ResponseEntity.ok(true);
    }

    @PostMapping(FIND_ALL)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<List<CashFlowResponseView>> findAll(@RequestBody PageRequestDto dto){

        return ResponseEntity.ok(cashFlowService.findAll(dto));
    }

    @GetMapping(FIND_BY_ID)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<CashFlowResponseView> findViewById(String id){

        return ResponseEntity.ok(cashFlowService.findViewById(id));
    }

    @PostMapping(GET_ALL_INCOME_AND_EXPENSE)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<CashFlowGetAllIncomeAndExpenseResponseDto> getAllIncomeAndExpense(){

        return ResponseEntity.ok(cashFlowService.getAllIncomeAndExpense());
    }



}
