package com.prolyzeai.utils;


import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import jakarta.mail.internet.InternetAddress;

public class UtilMethods
{


    public static void checkEmailFormat(String email) {
        try {
            InternetAddress addr = new InternetAddress(email);
            addr.validate();
        } catch (Exception e) {
            throw new ProlyzeException(ErrorType.EMAIL_NOT_VALID);
        }
    }

    public static void checkPhoneFormat(String phone) {
        // Sadece + ile başlayan ya da rakamdan başlayan numaralar, sadece rakam içerir
        String regex = "^\\+?\\d{8,15}$";
        if (!phone.matches(regex)) {
            throw new ProlyzeException(ErrorType.INVALID_PHONE_NUMBER_FORMAT);
        }
    }


}
