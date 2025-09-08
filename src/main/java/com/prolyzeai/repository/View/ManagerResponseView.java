package com.prolyzeai.repository.View;


import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public interface ManagerResponseView
{
    UUID getId();
    String getName();
    String getSurname();
    String getPhoneNumber();
    @Value("#{target.auth.email}")
    String getEmail();
}
