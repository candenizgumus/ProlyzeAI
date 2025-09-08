package com.prolyzeai.service;


import com.prolyzeai.repository.CashFlowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CashFlowService
{
    private final CashFlowRepository cashFlowRepository;


}
