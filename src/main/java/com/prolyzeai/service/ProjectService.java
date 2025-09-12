package com.prolyzeai.service;


import com.prolyzeai.dto.request.*;
import com.prolyzeai.entities.Auth;
import com.prolyzeai.entities.Company;
import com.prolyzeai.entities.Manager;
import com.prolyzeai.entities.Project;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import com.prolyzeai.repository.ProjectRepository;
import com.prolyzeai.repository.View.ProjectResponseView;
import com.prolyzeai.utils.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProjectService
{
    private final ProjectRepository projectRepository;
    private final ManagerService managerService;
    private final ItemService itemService;


    public Project findById(String id){
        return projectRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ProlyzeException(ErrorType.PROJECT_NOT_FOUND));
    }


    public Project save(ProjectSaveRequestDto dto)
    {
        Auth auth = SessionManager.getAuthFromToken();
        Manager manager = managerService.findByAuth(auth);

        //Eğer aylık proje oluşturma limiti aşıldıysa hata veriyor
        if (getCurrentMonthCreatedProjectCount(manager.getCompany()) > manager.getCompany().getMonthlyProjectLimit())
        {
            throw new ProlyzeException(ErrorType.PROJECT_LIMIT_EXCEEDED , manager.getCompany().getMonthlyProjectLimit().toString());
        }

        return projectRepository.save(Project.builder()
                .name(dto.name())
                .description(dto.description())
                .company(manager.getCompany())
                .agreedPrice(dto.agreedPrice())
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .plannedEndDate(dto.plannedEndDate())
                .build());

    }

    public Project saveForDemoData(ProjectSaveRequestDto dto, Manager manager)
    {
        return projectRepository.save(Project.builder()
                .name(dto.name())
                .description(dto.description())
                .company(manager.getCompany())
                .agreedPrice(dto.agreedPrice())
                .startDate(dto.startDate())
                .build());

    }

    public Boolean delete(String id)
    {
        Project project = findById(id);
        project.setStatus(EStatus.DELETED);
        projectRepository.save(project);
        return true;
    }

    public Boolean update(ProjectUpdateRequestDto dto)
    {
        Project project = findById(dto.id());

        //Eğer tarih bitiş tarihten önce ise hata ver
        if (dto.endDate() != null && dto.endDate().isBefore(dto.startDate()))
        {
            throw new ProlyzeException(ErrorType.INVALID_DATE);
        }


        project.setName(dto.name());
        project.setDescription(dto.description());
        project.setAgreedPrice(dto.agreedPrice());
        project.setStartDate(dto.startDate());
        project.setEndDate(dto.endDate());
        project.setIsCompleted(dto.isCompleted());
        project.setPlannedEndDate(dto.plannedEndDate());
        projectRepository.save(project);
        return true;
    }

    public List<ProjectFindAllResponseDto> findAll(PageRequestDto dto)
    {
        Auth auth = SessionManager.getAuthFromToken();
        Manager manager = managerService.findByAuth(auth);


        //TODO BURAYA BİR MANTIK LAZIM DÖNEN VERİ 0.1 DÖNDÜĞÜNDE ASLINDA %90 KARLI fakat 1.5 dönerse %50 zararda. belki veriden 1 çıkarabiliriz.
        List<ProjectResponseView> projectList = projectRepository.findAllByNameContainingIgnoreCaseAndStatusIsNotAndCompanyOrderByNameAsc(dto.searchText(), EStatus.DELETED,manager.getCompany(), PageRequest.of(dto.page(), dto.pageSize()));
        List<ProjectFindAllResponseDto> projectResponseViewList = new ArrayList<>();
        for (ProjectResponseView responseView : projectList)
        {
            // Projeye ait karlılık oranı "0.63" gibi dönmeli
            Double totalCost =  itemService.findTotalItemCostByProjectId(responseView.getId());

            double ratio = 0.0;
            if (responseView.getAgreedPrice() != null && responseView.getAgreedPrice() != 0) {
                ratio = 1- (totalCost / responseView.getAgreedPrice());
            }

            // Oranı 2 basamaklı hale getir (0.56 gibi)
            double profitability = BigDecimal.valueOf(ratio)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();

            //Kalan bütçeyi hesaplama
            double remainingBudget = responseView.getAgreedPrice() - totalCost;


            projectResponseViewList.add(new ProjectFindAllResponseDto(responseView.getId().toString(),responseView.getName(),responseView.getDescription(),responseView.getAgreedPrice(), remainingBudget , responseView.getStartDate(),responseView.getEndDate(),responseView.getPlannedEndDate(), responseView.getIsCompleted(),profitability));
        }
        return projectResponseViewList ;

    }

    public ProjectResponseView findViewById(String id)
    {
        return  projectRepository.findViewById(UUID.fromString(id)).orElseThrow(() -> new ProlyzeException(ErrorType.PROJECT_NOT_FOUND));

    }

    public Integer getCurrentMonthCreatedProjectCount(Company company) {
        LocalDate now = LocalDate.now();

        LocalDate start = now.withDayOfMonth(1); // ayın ilk günü
        LocalDate end = now.withDayOfMonth(now.lengthOfMonth()); // ayın son günü

        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(23, 59, 59);

        return projectRepository.countAllByStatusIsNotAndCreatedAtBetweenAndCompany(EStatus.DELETED, startDateTime, endDateTime,company);
    }


    public Double sumAgreedPriceForYear(EStatus eStatus, Company company, LocalDateTime startOfYear, LocalDateTime endOfYear)
    {
        return projectRepository.sumAgreedPriceForYear(eStatus, company, startOfYear, endOfYear);
    }

    public List<UUID> findAllIdsByStatusIsNotAndCreatedAtBetweenAndCompany(EStatus eStatus, Company company, LocalDateTime startOfYear, LocalDateTime endOfYear){
        return projectRepository.findAllIdsByStatusIsNotAndCreatedAtBetweenAndCompany(eStatus, startOfYear, endOfYear , company);
    }
}
