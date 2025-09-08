package com.prolyzeai.service;


import com.prolyzeai.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProjectService
{
    private final ProjectRepository projectRepository;


}
