package com.prolyzeai.service;


import com.prolyzeai.dto.request.*;
import com.prolyzeai.entities.Auth;
import com.prolyzeai.entities.Manager;
import com.prolyzeai.entities.Project;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import com.prolyzeai.repository.ProjectRepository;
import com.prolyzeai.repository.View.CategoryResponseView;
import com.prolyzeai.repository.View.ProjectResponseView;
import com.prolyzeai.utils.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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


    public Boolean save(ProjectSaveRequestDto dto)
    {
        Auth auth = SessionManager.getAuthFromToken();
        Manager manager = managerService.findByAuth(auth);

        projectRepository.save(Project.builder()
                .name(dto.name())
                .description(dto.description())
                .company(manager.getCompany())
                .agreedPrice(dto.agreedPrice())
                .startDate(dto.startDate())
                .build());

        return true;

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
        //Eğer tarih bitiş tarihten önce ise hata ver
        if (dto.startDate().isBefore(dto.endDate()))
        {
            throw new ProlyzeException(ErrorType.INVALID_DATE);
        }
        Project project = findById(dto.id());
        project.setName(dto.name());
        project.setDescription(dto.description());
        project.setAgreedPrice(dto.agreedPrice());
        project.setStartDate(dto.startDate());
        project.setEndDate(dto.endDate());
        project.setIsCompleted(dto.isCompleted());
        projectRepository.save(project);
        return true;
    }

    public List<ProjectFindAllResponseDto> findAll(PageRequestDto dto)
    {
        //TODO BURAYA BİR MANTIK LAZIM DÖNEN VERİ 0.1 DÖNDÜĞÜNDE ASLINDA %90 KARLI fakat 1.5 dönerse %50 zararda. belki veriden 1 çıkarabiliriz.
        List<ProjectResponseView> projectList = projectRepository.findAllByNameContainingIgnoreCaseAndStatusIsNotOrderByNameAsc(dto.searchText(), EStatus.DELETED, PageRequest.of(dto.page(), dto.pageSize()));
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


            projectResponseViewList.add(new ProjectFindAllResponseDto(responseView.getId().toString(),responseView.getName(),responseView.getDescription(),responseView.getAgreedPrice(),responseView.getStartDate(),responseView.getEndDate(),responseView.getIsCompleted(),profitability));
        }
        return projectResponseViewList ;

    }

    public ProjectResponseView findViewById(String id)
    {
        return  projectRepository.findViewById(UUID.fromString(id)).orElseThrow(() -> new ProlyzeException(ErrorType.PROJECT_NOT_FOUND));

    }
}
