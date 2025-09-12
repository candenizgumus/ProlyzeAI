package com.prolyzeai.controller;


import com.prolyzeai.dto.request.ItemGetAllItemsOfProjectRequestDto;
import com.prolyzeai.dto.request.ItemSaveRequestDto;
import com.prolyzeai.dto.request.ItemUpdateRequestDto;
import com.prolyzeai.dto.request.PageRequestDto;
import com.prolyzeai.repository.View.ItemResponseView;
import com.prolyzeai.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.prolyzeai.constants.Endpoints.*;


@RestController
@RequestMapping(ROOT + ITEM)
@RequiredArgsConstructor
public class ItemController
{

    private final ItemService itemService;

    @PostMapping(SAVE)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<Boolean> save(@RequestBody ItemSaveRequestDto dto){

        itemService.save(dto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping(DELETE)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<Boolean> delete(String id){

        return ResponseEntity.ok(itemService.delete(id));
    }

    @PutMapping(UPDATE)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<Boolean> update(@RequestBody ItemUpdateRequestDto dto){

        itemService.update(dto);
        return ResponseEntity.ok(true);
    }

    @PostMapping(FIND_ALL)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<List<ItemResponseView>> findAll(@RequestBody PageRequestDto dto){

        return ResponseEntity.ok(itemService.findAll(dto));
    }

    @PostMapping(GET_ALL_ITEMS_OF_PROJECT)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<List<ItemResponseView>> getAllItemsOfProject(@RequestBody ItemGetAllItemsOfProjectRequestDto dto){

        return ResponseEntity.ok(itemService.getAllItemsOfProject(dto));
    }

    @GetMapping(FIND_BY_ID)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<ItemResponseView> findViewById(String id){

        return ResponseEntity.ok(itemService.findViewById(id));
    }



}
