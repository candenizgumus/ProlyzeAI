package com.prolyzeai.service;


import com.prolyzeai.repository.ItemRepository;
import com.prolyzeai.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ManagerService
{
    private final ManagerRepository managerRepository;


}
