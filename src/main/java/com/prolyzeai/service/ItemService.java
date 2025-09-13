package com.prolyzeai.service;


import com.prolyzeai.dto.request.ItemGetAllItemsOfProjectRequestDto;
import com.prolyzeai.dto.request.ItemSaveRequestDto;
import com.prolyzeai.dto.request.ItemUpdateRequestDto;
import com.prolyzeai.dto.request.PageRequestDto;
import com.prolyzeai.entities.*;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import com.prolyzeai.repository.ItemRepository;
import com.prolyzeai.repository.View.ItemResponseView;
import com.prolyzeai.utils.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ItemService
{
    private final ItemRepository itemRepository;
    private final CategoryService categoryService;
    @Lazy
    @Autowired
    private ProjectService projectService;
    private final ManagerService managerService;


    public Boolean save(ItemSaveRequestDto dto)
    {
        Category category = categoryService.findById(dto.categoryId());
        Project project = projectService.findById(dto.projectId());

        itemRepository.save(Item.builder()
                .category(category)
                .project(project)
                .description(dto.description())
                .unitPrice(dto.unitPrice())
                .quantity(dto.quantity())
                .build());

        return true;
    }

    public Boolean delete(String id)
    {
        Item item = findById(id);
        item.setStatus(EStatus.DELETED);
        itemRepository.save(item);
        return true;
    }

    public Item findById(String id)
    {
        return itemRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ProlyzeException(ErrorType.ITEM_NOT_FOUND));
    }

    public Boolean update(ItemUpdateRequestDto dto)
    {
        Category category = categoryService.findById(dto.categoryId());
        Item item = findById(dto.id());
        item.setCategory(category);
        item.setDescription(dto.description());
        item.setUnitPrice(dto.unitPrice());
        item.setQuantity(dto.quantity());
        itemRepository.save(item);
        return true;
    }

    public List<ItemResponseView> findAll(PageRequestDto dto)
    {
        Auth auth = SessionManager.getAuthFromToken();
        Manager manager = managerService.findByAuth(auth);

        return itemRepository.findAllByDescriptionContainingIgnoreCaseAndStatusIsNotAndCategory_CompanyOrderByDescriptionAsc(dto.searchText(), EStatus.DELETED, manager.getCompany(), PageRequest.of(dto.page(), dto.pageSize()));

    }

    public ItemResponseView findViewById(String id)
    {
        return itemRepository.findViewById(UUID.fromString(id));

    }

    public Double findTotalItemCostByProjectId(UUID projectId)
    {
        return itemRepository.findTotalItemCostByProjectId(projectId);

    }

    public double findTotalItemCostByProjectIds(List<UUID> projectIds, EStatus eStatus , Company company){
        return itemRepository.findTotalItemCostByProjectIds(projectIds, eStatus, company);
    }

    public Map<String, Double> getCategoryExpensesForCurrentYear(EStatus eStatus, Company company) {
        LocalDateTime startOfYear = LocalDate.now().withDayOfYear(1).atStartOfDay();
        LocalDateTime endOfYear = LocalDate.now().withMonth(12).withDayOfMonth(31).atTime(23, 59, 59);

        List<Object[]> results = itemRepository.findTopCategoryExpensesForYear(
                eStatus, company, startOfYear, endOfYear, true, PageRequest.of(0, 5)
        );


        Map<String, Double> categoryExpenses = new HashMap<>();
        for (Object[] row : results) {
            String categoryName = (String) row[0];
            Double total = (Double) row[1];
            categoryExpenses.put(categoryName, total);
        }
        return categoryExpenses;
    }

    public List<ItemResponseView> getAllItemsOfProject(ItemGetAllItemsOfProjectRequestDto dto)
    {

        return itemRepository.findAllByDescriptionContainingIgnoreCaseAndStatusIsNotAndProject_IdOrderByDescriptionAsc(dto.searchText(), EStatus.DELETED, UUID.fromString(dto.projectId()), PageRequest.of(dto.page(), dto.pageSize()));

    }
}
