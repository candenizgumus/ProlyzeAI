package com.prolyzeai.service;


import com.prolyzeai.dto.request.ManagerSaveRequestDto;
import com.prolyzeai.dto.request.PageRequestDto;
import com.prolyzeai.dto.request.ProjectSaveRequestDto;
import com.prolyzeai.dto.request.ProjectUpdateRequestDto;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProjectService
{
    private final ProjectRepository projectRepository;
    private final ManagerService managerService;


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

    public List<ProjectResponseView> findAll(PageRequestDto dto)
    {
        return  projectRepository.findAllByNameContainingIgnoreCaseAndStatusIsNotOrderByNameAsc(dto.searchText(),EStatus.DELETED, PageRequest.of(dto.page(), dto.pageSize()));

    }

    public ProjectResponseView findViewById(String id)
    {
        return  projectRepository.findViewById(UUID.fromString(id)).orElseThrow(() -> new ProlyzeException(ErrorType.PROJECT_NOT_FOUND));

    }
}
