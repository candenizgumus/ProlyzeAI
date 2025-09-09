package com.prolyzeai.repository;


import com.prolyzeai.entities.Project;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.repository.View.CategoryResponseView;
import com.prolyzeai.repository.View.ProjectResponseView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID>
{

    List<ProjectResponseView> findAllByNameContainingIgnoreCaseAndStatusIsNotOrderByNameAsc(String s, EStatus eStatus, PageRequest of);
    Optional<ProjectResponseView> findViewById(UUID id);
}
