package com.prolyzeai.controller;


import com.prolyzeai.dto.request.*;
import com.prolyzeai.repository.View.AdminResponseView;
import com.prolyzeai.repository.View.CategoryResponseView;
import com.prolyzeai.service.AdminService;
import com.prolyzeai.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.prolyzeai.constants.Endpoints.*;


@RestController
@RequestMapping(ROOT + CATEGORY)
@RequiredArgsConstructor
public class CategoryController
{

    private final CategoryService categoryService;

    @PostMapping(SAVE)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<Boolean> save(@RequestBody CategorySaveRequestDto dto){

        categoryService.save(dto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping(DELETE)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<Boolean> delete(String id){

        return ResponseEntity.ok(categoryService.delete(id));
    }

    @PutMapping(UPDATE)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<Boolean> update(@RequestBody CategoryUpdateRequestDto dto){

        categoryService.update(dto);
        return ResponseEntity.ok(true);
    }

    @PostMapping(FIND_ALL)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<List<CategoryResponseView>> findAll(@RequestBody PageRequestDto dto){

        return ResponseEntity.ok(categoryService.findAll(dto));
    }

    @GetMapping(FIND_BY_ID)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<CategoryResponseView> findViewById(String id){

        return ResponseEntity.ok(categoryService.findViewById(id));
    }



}
