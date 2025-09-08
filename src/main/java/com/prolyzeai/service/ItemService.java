package com.prolyzeai.service;


import com.prolyzeai.repository.CategoryRepository;
import com.prolyzeai.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ItemService
{
    private final ItemRepository itemRepository;


}
