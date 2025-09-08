package com.prolyzeai.utils;

import com.prolyzeai.dto.request.AdminSaveRequestDto;
import com.prolyzeai.service.AdminService;
import com.prolyzeai.service.AuthService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DemoDataGenerator
{
    private final AuthService authService;
    private final AdminService adminService;


    @PostConstruct
    public void generateDemoData() {
        generateAdmin();
    }

    //If admin is not created, creates admin.
    private void generateAdmin() {

        if (!authService.existsByEmail("prolyzeai@gmail.com"))
        {
            adminService.save(new AdminSaveRequestDto("Admin", "Admin", "prolyzeai@gmail.com", "9L4l4A\\/ugQT"));
        }

    }


}
