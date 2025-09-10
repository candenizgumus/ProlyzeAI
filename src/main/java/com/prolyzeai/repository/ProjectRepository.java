package com.prolyzeai.repository;


import com.prolyzeai.entities.Company;
import com.prolyzeai.entities.Project;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.repository.View.ProjectResponseView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID>
{

    List<ProjectResponseView> findAllByNameContainingIgnoreCaseAndStatusIsNotAndCompanyOrderByNameAsc(String s, EStatus eStatus, Company company, PageRequest of);
    Optional<ProjectResponseView> findViewById(UUID id);

    Integer countAllByStatusIsNotAndCreatedAtBetweenAndCompany(EStatus eStatus, LocalDateTime startDate, LocalDateTime endDate, Company company);

}
