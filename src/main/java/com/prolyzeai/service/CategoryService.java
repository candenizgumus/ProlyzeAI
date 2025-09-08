package com.prolyzeai.service;


import com.prolyzeai.repository.CashFlowRepository;
import com.prolyzeai.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CategoryService
{
    private final CategoryRepository categoryRepository;


}
