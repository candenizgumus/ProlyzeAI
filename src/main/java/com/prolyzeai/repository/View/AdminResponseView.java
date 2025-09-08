package com.prolyzeai.repository.View;


import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public interface AdminResponseView
{
    UUID getId();
    String getName();
    String getSurname();
    @Value("#{target.auth.email}")
    String getEmail();
}
