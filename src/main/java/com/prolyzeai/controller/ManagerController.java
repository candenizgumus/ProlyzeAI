package com.prolyzeai.controller;


import com.prolyzeai.dto.request.ManagerCreateAccountRequestDto;
import com.prolyzeai.dto.request.ManagerSaveRequestDto;
import com.prolyzeai.dto.request.ManagerUpdateRequestDto;
import com.prolyzeai.repository.View.ManagerResponseView;
import com.prolyzeai.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.prolyzeai.constants.Endpoints.*;


@RestController
@RequestMapping(ROOT + MANAGER)
@RequiredArgsConstructor
public class ManagerController
{

    private final ManagerService managerService;

    @PostMapping(SAVE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Boolean> save(@RequestBody ManagerSaveRequestDto dto){

        managerService.save(dto);
        return ResponseEntity.ok(true);
    }

    @PostMapping(CREATE_ACCOUNT)
    public ResponseEntity<Boolean> createAccount(@RequestBody ManagerCreateAccountRequestDto dto){

        managerService.createAccount(dto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping(DELETE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Boolean> delete(String id){

        return ResponseEntity.ok(managerService.delete(id));
    }

    @PutMapping(UPDATE)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<Boolean> update(@RequestBody ManagerUpdateRequestDto dto){

        managerService.update(dto);
        return ResponseEntity.ok(true);
    }

    @GetMapping(FIND_BY_ID)
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<ManagerResponseView> findViewById(String id){

        return ResponseEntity.ok(managerService.findViewById(id));
    }

    @GetMapping(FIND_BY_TOKEN)
    public ResponseEntity<ManagerResponseView> findByToken(){
        return ResponseEntity.ok(managerService.findByToken());
    }


}
