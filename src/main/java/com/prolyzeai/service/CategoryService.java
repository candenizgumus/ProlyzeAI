package com.prolyzeai.service;


import com.prolyzeai.dto.request.CategorySaveRequestDto;
import com.prolyzeai.dto.request.CategoryUpdateRequestDto;
import com.prolyzeai.dto.request.PageRequestDto;
import com.prolyzeai.entities.Auth;
import com.prolyzeai.entities.Category;
import com.prolyzeai.entities.Company;
import com.prolyzeai.entities.Manager;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import com.prolyzeai.repository.CategoryRepository;
import com.prolyzeai.repository.View.CategoryResponseView;
import com.prolyzeai.utils.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CategoryService
{
    private final CategoryRepository categoryRepository;
    @Lazy @Autowired
    private ManagerService managerService;


    public Category save(CategorySaveRequestDto dto)
    {

        Auth auth = SessionManager.getAuthFromToken();
        Manager manager = managerService.findByAuth(auth);

        return categoryRepository.save(Category.builder()
                .name(dto.name())
                .company(manager.getCompany())
                .build());
    }

    public Category saveForDemoData(String name, Company company)
    {
        return categoryRepository.save(Category.builder()
                .name(name)
                .company(company)
                .build());
    }

    public Category update(CategoryUpdateRequestDto dto)
    {

        Category category = categoryRepository.findById(UUID.fromString(dto.id())).orElseThrow(() -> new ProlyzeException(ErrorType.CATEGORY_NOT_FOUND));
        category.setName(dto.name());
        return categoryRepository.save(category);

    }

    public Boolean delete(String id)
    {

        Category category = categoryRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ProlyzeException(ErrorType.CATEGORY_NOT_FOUND));
        category.setStatus(EStatus.DELETED);
        categoryRepository.save(category);
        return true;


    }


    public List<CategoryResponseView> findAll(PageRequestDto dto)
    {
        Auth auth = SessionManager.getAuthFromToken();
        Manager manager = managerService.findByAuth(auth);

        return  categoryRepository.findAllByNameContainingIgnoreCaseAndStatusIsNotAndCompanyOrderByNameAsc(dto.searchText(),EStatus.DELETED, manager.getCompany(), PageRequest.of(dto.page(), dto.pageSize()));

    }

    public CategoryResponseView findViewById(String id)
    {
        return  categoryRepository.findViewById(UUID.fromString(id)).orElseThrow(() -> new ProlyzeException(ErrorType.CATEGORY_NOT_FOUND));

    }

    public Category findById(String id)
    {
        return  categoryRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ProlyzeException(ErrorType.CATEGORY_NOT_FOUND));

    }
}
