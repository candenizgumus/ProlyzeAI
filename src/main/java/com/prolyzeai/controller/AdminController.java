package com.prolyzeai.controller;


import com.prolyzeai.dto.request.AdminSaveRequestDto;
import com.prolyzeai.dto.request.AdminUpdateRequestDto;
import com.prolyzeai.dto.request.PageRequestDto;
import com.prolyzeai.repository.View.AdminResponseView;
import com.prolyzeai.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.prolyzeai.constants.Endpoints.*;


@RestController
@RequestMapping(ROOT + ADMIN)
@RequiredArgsConstructor
public class AdminController
{

    private final AdminService adminService;

    @PostMapping(SAVE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Boolean> save(@RequestBody AdminSaveRequestDto dto){

        adminService.save(dto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping(DELETE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Boolean> delete(String uuid){

        return ResponseEntity.ok(adminService.delete(uuid));
    }

    @PutMapping(UPDATE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Boolean> update(@RequestBody AdminUpdateRequestDto dto){

        adminService.update(dto);
        return ResponseEntity.ok(true);
    }

    @PostMapping(FIND_ALL)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<AdminResponseView>> findAll(@RequestBody PageRequestDto dto){

        return ResponseEntity.ok(adminService.findAll(dto));
    }

    @GetMapping(FIND_BY_ID)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<AdminResponseView> findViewById(String id){

        return ResponseEntity.ok(adminService.findViewById(id));
    }

    @GetMapping(FIND_BY_TOKEN)
    public ResponseEntity<AdminResponseView> findByToken(){

        return ResponseEntity.ok(adminService.findByToken());
    }



}
