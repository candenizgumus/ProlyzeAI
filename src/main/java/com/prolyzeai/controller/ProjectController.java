package com.prolyzeai.controller;


import com.prolyzeai.dto.request.*;
import com.prolyzeai.repository.View.CategoryResponseView;
import com.prolyzeai.repository.View.ManagerResponseView;
import com.prolyzeai.repository.View.ProjectResponseView;
import com.prolyzeai.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.prolyzeai.constants.Endpoints.*;


@RestController
@RequestMapping(ROOT + PROJECT)
@RequiredArgsConstructor
public class ProjectController
{

    private final ProjectService projectService;

    @PostMapping(SAVE)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<Boolean> save(@RequestBody ProjectSaveRequestDto dto){

        projectService.save(dto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping(DELETE)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<Boolean> delete(String id){

        return ResponseEntity.ok(projectService.delete(id));
    }

    @PutMapping(UPDATE)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<Boolean> update(@RequestBody ProjectUpdateRequestDto dto){

        projectService.update(dto);
        return ResponseEntity.ok(true);
    }

    @PostMapping(FIND_ALL)
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public ResponseEntity<List<ProjectFindAllResponseDto>> findAll(@RequestBody PageRequestDto dto){

        return ResponseEntity.ok(projectService.findAll(dto));
    }

    @GetMapping(FIND_BY_ID)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<ProjectResponseView> findViewById(String id){

        return ResponseEntity.ok(projectService.findViewById(id));
    }



}
