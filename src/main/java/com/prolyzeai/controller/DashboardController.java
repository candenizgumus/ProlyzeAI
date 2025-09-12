package com.prolyzeai.controller;


import com.prolyzeai.dto.request.DashboardGetDashboardDataRequestDto;
import com.prolyzeai.service.DashboardService;
import com.prolyzeai.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.prolyzeai.constants.Endpoints.*;


@RestController
@RequestMapping(ROOT + DASHBOARD)
@RequiredArgsConstructor
public class DashboardController
{

    private final DashboardService dashboardService;

    @GetMapping(GET_DASHBOARD_DATA)
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public ResponseEntity<DashboardGetDashboardDataRequestDto> getDashboardData(){

        return ResponseEntity.ok(dashboardService.getDashboardData());
    }


}
