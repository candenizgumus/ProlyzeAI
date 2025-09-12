package com.prolyzeai.service;


import com.prolyzeai.dto.request.DashboardGetDashboardDataRequestDto;
import com.prolyzeai.entities.Auth;
import com.prolyzeai.entities.Company;
import com.prolyzeai.entities.Manager;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.utils.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class DashboardService
{
    private final ProjectService projectService;
    private final ItemService itemService;
    private final ManagerService managerService;


    public DashboardGetDashboardDataRequestDto getDashboardData() {
        Auth auth = SessionManager.getAuthFromToken();
        Manager manager = managerService.findByAuth(auth);

        // Bu yılın başı (1 Ocak 00:00:00)
        LocalDateTime startOfYear = LocalDate.now()
                .withDayOfYear(1)
                .atStartOfDay();

        // Bu yılın sonu (31 Aralık 23:59:59)
        LocalDateTime endOfYear = LocalDate.now()
                .withMonth(12)
                .withDayOfMonth(31)
                .atTime(23, 59, 59);

        // Toplam agreedPrice
        Double totalYearlyAgreedPriceForCurrentYear =
                getTotalAgreedPriceForCurrentYear(EStatus.DELETED, manager.getCompany());

        // ID listesi
        List<UUID> projectIds = projectService.findAllIdsByStatusIsNotAndCreatedAtBetweenAndCompany(
                EStatus.DELETED,
                manager.getCompany(),
                startOfYear,
                endOfYear
        );

        Double totalYearlyCostOfProjects = itemService.findTotalItemCostByProjectIds(projectIds, EStatus.DELETED, manager.getCompany());

        return new DashboardGetDashboardDataRequestDto(totalYearlyCostOfProjects, totalYearlyAgreedPriceForCurrentYear);
    }


    public Double getTotalAgreedPriceForCurrentYear(EStatus eStatus, Company company) {
        LocalDate now = LocalDate.now();
        LocalDateTime startOfYear = now.withDayOfYear(1).atStartOfDay();
        LocalDateTime endOfYear = now.withMonth(12).withDayOfMonth(31).atTime(23, 59, 59);

        return projectService.sumAgreedPriceForYear(eStatus, company, startOfYear, endOfYear);
    }

}
