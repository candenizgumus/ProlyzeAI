package com.prolyzeai.service;


import com.prolyzeai.dto.request.CashFlowSaveRequestDto;
import com.prolyzeai.dto.request.CashFlowUpdateRequestDto;
import com.prolyzeai.dto.request.CategoryUpdateRequestDto;
import com.prolyzeai.dto.request.PageRequestDto;
import com.prolyzeai.dto.response.CashFlowGetAllIncomeAndExpenseResponseDto;
import com.prolyzeai.entities.Auth;
import com.prolyzeai.entities.CashFlow;
import com.prolyzeai.entities.Category;
import com.prolyzeai.entities.Manager;
import com.prolyzeai.entities.enums.ECashFlowType;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import com.prolyzeai.repository.CashFlowRepository;
import com.prolyzeai.repository.View.CashFlowResponseView;
import com.prolyzeai.repository.View.CategoryResponseView;
import com.prolyzeai.utils.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CashFlowService
{
    private final CashFlowRepository cashFlowRepository;
    private final CategoryService categoryService;
    private final ManagerService managerService;


    public CashFlow save(CashFlowSaveRequestDto dto)
    {
        Category category = categoryService.findById(dto.categoryId());

        CashFlow cashFlow = cashFlowRepository.save(CashFlow.builder()
                .category(category)
                .date(dto.date())
                .description(dto.description())
                .cashFlowType(dto.cashFlowType())
                .build());

        //Eğer para giriş ise "+" çıkış ise "-" olarak kaydediliyor.
        if (dto.cashFlowType().equals(ECashFlowType.GIRIS))
        {
            cashFlow.setAmount(dto.amount());
        }
        else
        {
            cashFlow.setAmount(-dto.amount());
        }

        return cashFlow;
    }


    public Boolean delete(String id)
    {
        CashFlow cashFlow = cashFlowRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ProlyzeException(ErrorType.CASH_FLOW_NOT_FOUND));
        cashFlow.setStatus(EStatus.DELETED);
        cashFlowRepository.save(cashFlow);
        return true;
    }

    public Boolean update(CashFlowUpdateRequestDto dto)
    {
        Category category = categoryService.findById(dto.categoryId());
        CashFlow cashFlow = cashFlowRepository.findById(UUID.fromString(dto.id())).orElseThrow(() -> new ProlyzeException(ErrorType.CASH_FLOW_NOT_FOUND));
        cashFlow.setDate(dto.date());
        cashFlow.setDescription(dto.description());
        cashFlow.setAmount(dto.amount());
        cashFlow.setCategory(category);
        cashFlowRepository.save(cashFlow);
        return true;
    }

    public List<CashFlowResponseView> findAll(PageRequestDto dto)
    {
        return cashFlowRepository.findAllByDescriptionContainingIgnoreCaseAndStatusIsNotOrderByDateDesc(dto.searchText(),EStatus.DELETED, PageRequest.of(dto.page(), dto.pageSize()));
    }

    public CashFlowResponseView findViewById(String id)
    {
        return cashFlowRepository.findViewById(UUID.fromString(id)).orElseThrow(() -> new ProlyzeException(ErrorType.CASH_FLOW_NOT_FOUND));
    }

    public CashFlowGetAllIncomeAndExpenseResponseDto getAllIncomeAndExpense()
    {
        Auth auth = SessionManager.getAuthFromToken();
        Manager manager = managerService.findByAuth(auth);

        Double income = cashFlowRepository.sumAmountByCompanyIdAndType(manager.getCompany().getId(),ECashFlowType.GIRIS);
        Double expense = cashFlowRepository.sumAmountByCompanyIdAndType(manager.getCompany().getId(),ECashFlowType.CIKIS);
        Double balance = income + expense;

        return new CashFlowGetAllIncomeAndExpenseResponseDto(income,expense,balance);


    }
}
