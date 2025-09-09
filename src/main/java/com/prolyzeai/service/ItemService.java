package com.prolyzeai.service;


import com.prolyzeai.dto.request.ItemSaveRequestDto;
import com.prolyzeai.dto.request.ItemUpdateRequestDto;
import com.prolyzeai.dto.request.PageRequestDto;
import com.prolyzeai.entities.Category;
import com.prolyzeai.entities.Item;
import com.prolyzeai.entities.Project;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import com.prolyzeai.repository.ItemRepository;
import com.prolyzeai.repository.View.ItemResponseView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ItemService
{
    private final ItemRepository itemRepository;
    private final CategoryService categoryService;
    private final ProjectService projectService;


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

    public Item findById(String id){
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
        return  itemRepository.findAllByDescriptionContainingIgnoreCaseAndStatusIsNotOrderByDescriptionAsc(dto.searchText(),EStatus.DELETED, PageRequest.of(dto.page(), dto.pageSize()));

    }

    public ItemResponseView findViewById(String id)
    {
        return  itemRepository.findViewById(UUID.fromString(id));

    }
}
