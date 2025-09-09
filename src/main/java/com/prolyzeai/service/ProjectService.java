package com.prolyzeai.service;


import com.prolyzeai.entities.Project;
import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import com.prolyzeai.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProjectService
{
    private final ProjectRepository projectRepository;


    public Project findById(String id){
        return projectRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ProlyzeException(ErrorType.PROJECT_NOT_FOUND));
    }


}
