package com.prolyzeai.utils;


import com.prolyzeai.entities.Auth;
import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionManager
{
    public static Auth getAuthFromToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Auth) {
            return (Auth) principal;
        }

        throw new ProlyzeException(ErrorType.NOT_AUTHORIZED);
    }
}
